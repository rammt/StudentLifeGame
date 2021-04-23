package no.ntnu.tdt4240.game;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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

import org.w3c.dom.Document;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import no.ntnu.tdt4240.game.StudentLifeGame;
import no.ntnu.tdt4240.game.components.HighscoreComponent;
import no.ntnu.tdt4240.game.components.PlayerComponent;
import no.ntnu.tdt4240.game.components.ResourceGainerComponent;
import no.ntnu.tdt4240.game.screens.HighscoreScreen;

public class AndroidLauncher extends AndroidApplication implements FirebaseInterface {

	private static final int RC_SIGN_IN = 9001;
	private static final String TAG = "GoogleSignInActivity";

	private FirebaseAuth mAuth;
	private FirebaseFirestore db = FirebaseFirestore.getInstance();

	private GoogleSignInClient mSignInClient;

	private List<Map<String, Object>> highscoreList = new ArrayList<Map<String, Object>>();
	private ArrayList<ResourceGainerComponent> resourceGainers;
	private int rank;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new StudentLifeGame(this), config);


		GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
				.requestIdToken(getString(R.string.default_web_client_id))
				.requestEmail()
				.build();

		mSignInClient = GoogleSignIn.getClient(this, gso);

		mAuth = FirebaseAuth.getInstance();
	}

	@Override
	public void onSignInButtonClicked() {
		Intent signInIntent = mSignInClient.getSignInIntent();
		startActivityForResult(signInIntent, RC_SIGN_IN);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		// Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
		if (requestCode == RC_SIGN_IN) {
			Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
			try {
				// Google Sign In was successful, authenticate with Firebase
				GoogleSignInAccount account = task.getResult(ApiException.class);
				Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
				firebaseAuthWithGoogle(account.getIdToken());
			} catch (ApiException e) {
				// Google Sign In failed, update UI appropriately
				Log.w(TAG, "Google sign in failed", e);
			}
		}
	}

	private void firebaseAuthWithGoogle(String idToken) {
		AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
		mAuth.signInWithCredential(credential)
				.addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
					@Override
					public void onComplete(@NonNull Task<AuthResult> task) {
						if (task.isSuccessful()) {
							// Sign in success, update UI with the signed-in user's information
							System.out.println("signInWithCredential:success");
							FirebaseUser user = mAuth.getCurrentUser();
							handleLogin(user);
						} else {
							// If sign in fails, display a message to the user.
							System.out.println("signInWithCredential:failure, " + task.getException());
							handleLogin(null);
						}
					}
				});
	}


	private void handleLogin(FirebaseUser fb_user) {
		if (fb_user == null) {
			System.out.println("Didn't sign in");
		} else {
			System.out.println("Signed in");

			/*this.user.setName(fb_user.getDisplayName());
			getStats(this.user);*/
		}
	}

	/*
	private void setInfoFromFirebaseOnPlayer(final Entity player) {
		final FirebaseUser fb_user = mAuth.getCurrentUser();
		if (fb_user != null) {
			DocumentReference playerRef = db.collection("players").document(fb_user.getUid());

			playerRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
				@Override
				public void onComplete(@NonNull Task<DocumentSnapshot> task) {
					if (task.isSuccessful()) {
						DocumentSnapshot document = task.getResult();
						PlayerComponent pc = player.getComponent(PlayerComponent.class);
						if (document.exists()) {
							setStatsOnPlayerComponent(pc, document);
						} else {
							System.out.println("No data found");

						}
					} else {
						Log.d(TAG, "get failed with ", task.getException());
					}
				}


			});
		} else {
			System.out.println("NO USER FOUND");
		}

	}
*/

	private void setStatsOnPlayerComponent(PlayerComponent pc, DocumentSnapshot document) {
		Long kokTemp = document.getLong("kokCount");
		float kokCount = kokTemp.floatValue();
		Long clickCount = document.getLong("clickCount");
		String name = (String) document.get("name");
		Long lastSave = document.getLong("lastSave");
		List<Map<String, Object>> firebaseResourceGainers = (List<Map<String, Object>>) document.get("resourceGainers");
		boolean combinedButtons = document.getBoolean("combinedButtons");
		float clickValue = document.getDouble("clickValue").floatValue();
		this.resourceGainers = new ArrayList<>();
		for(Map<String, Object> map : firebaseResourceGainers) {
			ResourceGainerComponent tmpRgc = new ResourceGainerComponent();

			Long tmpPrice = (Long)map.get("price");
			Double tmpGain = (Double) map.get("gainPerSecond");
			tmpRgc.create(
					(String)map.get("name"),
					(String)map.get("desc"),
					tmpPrice.intValue(),
					tmpGain.floatValue()
			);
			resourceGainers.add(tmpRgc);
		}

		pc.setKokCount(kokCount);
		pc.setName(name);
		pc.setLastSave(lastSave);
		pc.setResourceGainers(resourceGainers);
		pc.setClickCount(clickCount);
		pc.setCombinedButtons(combinedButtons);
		pc.setClickValue(clickValue);
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
			playerMap.put("combinedButtons", pc.getCombinedButtons());
			playerMap.put("clickValue",pc.getClickValue());

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
							player.put("combinedButtons", pc.getCombinedButtons());
							player.put("clickValue", pc.getClickValue());

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
