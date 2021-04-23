package no.ntnu.tdt4240.game;

import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;

import com.badlogic.ashley.core.Entity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import no.ntnu.tdt4240.game.components.PlayerComponent;
import no.ntnu.tdt4240.game.components.ResourceGainerComponent;

public class AndroidFirebase implements Firebase {
    private static final String TAG = "GoogleSignInActivity";

    private FirebaseAuth mAuth;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private List<Map<String, Object>> highscoreList = new ArrayList<>();
    //private List<Map<String, Object>> resourceGainers = new ArrayList<>();
    private int rank;

    private static final AndroidFirebase androidFirebase = new AndroidFirebase();
    private static AndroidLauncher launcher;


    // SINGLETON FOR HANDLING FIREBASE INTERFACE TOWARDS ANDROID
    public static AndroidFirebase getInstance(AndroidLauncher launcher) {
        AndroidFirebase.launcher = launcher;
        return androidFirebase;
    }

    private AndroidFirebase() {
        mAuth = FirebaseAuth.getInstance();
    }


    public void signInWithCredentials(AndroidLauncher launcher, AuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(launcher, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //TODO handle login with some information
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            System.out.println("signInWithCredential:success");
                        } else {
                            // If sign in fails, display a message to the user.
                            System.out.println("signInWithCredential:failure, " + task.getException());
                        }
                    }
                });
    }




    private void setStatsOnPlayerComponent(PlayerComponent pc, DocumentSnapshot document) {
        Long kokTemp = document.getLong("kokCount");
        float kokCount = kokTemp.floatValue();
        Long clickCount = document.getLong("clickCount");
        String name = (String) document.get("name");
        Long lastSave = document.getLong("lastSave");
        List<Map<String, Object>> resourceGainers = (List<Map<String, Object>>) document.get("resourceGainers");

        pc.setKokCount(kokCount);
        pc.setName(name);
        pc.setLastSave(lastSave);
        pc.setResourceGainers(resourceGainers);
        pc.setClickCount(clickCount);
    }

    public boolean isLoggedIn() {
        FirebaseUser fb_user = mAuth.getCurrentUser();

        return fb_user != null;
    }

    private void handleLogin(FirebaseUser fb_user) {
        if (fb_user == null) {
            System.out.println("Didn't sign in");
        } else {
            System.out.println("Signed in");
        }
    }

    @Override
    public void startSignInActivity() {
        launcher.startSignInForResult();
    }

    @Override
    public void savePlayerStats(Entity player) {
        FirebaseUser fb_user = mAuth.getCurrentUser();

        if (fb_user != null) {
            PlayerComponent pc = player.getComponent(PlayerComponent.class);

            Map<String, Object> playerMap = new HashMap<>();

            playerMap.put("kokCount", pc.getKokCount());
            playerMap.put("name", fb_user.getDisplayName());
            playerMap.put("lastSave", new Date().getTime());
            playerMap.put("resourceGainers", pc.getResourceGainers());
            playerMap.put("clickCount", pc.getClickCount());

            db.collection("players").document(fb_user.getUid()).set(playerMap);
        }
    }

    public List<Map<String, Object>> getHighscore() {
        CollectionReference colRefPlayers = db.collection("players");
        colRefPlayers.orderBy("kokCount", Query.Direction.DESCENDING).limit(5).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            highscoreList.clear();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                highscoreList.add(document.getData());
                                Log.d(TAG, document.getId() + " => " + document.getData());
                            }

                        } else {
                            Log.d(TAG, "Error getting documents: " + task.getException());
                        }
                    }
                });
        return highscoreList;
    }

    public void getResourceGainers(final List<Map<String, Object>> resourceGainers) {
        CollectionReference resourceGainersReference = db.collection("resource_gainers");
        resourceGainersReference.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            resourceGainers.clear();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                resourceGainers.add(document.getData());
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                System.out.println("RGsize in snapshop: " + resourceGainers.size());
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: " + task.getException());
                        }
                    }
                });
    }


    @Override
    public void getPlayerStats(final Entity player) {
        final FirebaseUser fb_user = mAuth.getCurrentUser();
        if (fb_user != null) {
            DocumentReference userStatsRef = db.collection("players").document(fb_user.getUid());

            userStatsRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        PlayerComponent pc = player.getComponent(PlayerComponent.class);
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            setStatsOnPlayerComponent(pc, document);
                        } else {
                            Map<String, Object> player = new HashMap<>();

                            player.put("kokCount", pc.getKokCount());
                            String displayName = fb_user.getDisplayName();
                            player.put("name", displayName);
                            pc.setName(displayName);
                            player.put("lastSave", pc.getLastSave());
                            player.put("resourceGainers", pc.getResourceGainers());
                            player.put("clickCount",pc.getClickCount());

                            db.collection("players").document(fb_user.getUid()).set(player);
                            Log.d(TAG, "No such document");
                        }
                    } else {
                        Log.d(TAG, "get failed with ", task.getException());
                    }
                }
            });
        }
    }


    public int getRank(PlayerComponent pc) {
        CollectionReference colRefPlayers = db.collection("players");
        colRefPlayers.orderBy("kokCount", Query.Direction.DESCENDING)
                .whereGreaterThan("kokCount", pc.getKokCount()).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            rank = 1;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                rank += 1;
                                Log.d(TAG, document.getId() + " => " + document.getData());
                            }

                        } else {
                            Log.d(TAG, "Error getting documents: " + task.getException());
                        }
                    }
                });
        return rank;
    }
}
