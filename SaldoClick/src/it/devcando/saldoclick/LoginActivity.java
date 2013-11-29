package it.devcando.saldoclick;

import it.devcando.saldoclick.R;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Activity which displays a login screen to the user, offering registration as
 * well.
 */
public class LoginActivity extends Activity {
	/**
	 * Keep track of the login task to ensure we can cancel it if requested.
	 */
	private UserLoginTask mAuthTask = null;

	// Values for email and password at the time of the login attempt.
	private String mEmail;
	private String mPassword;

	// UI references.
	private EditText mEmailView;
	private EditText mPasswordView;
	private View mLoginFormView;
	private View mLoginStatusView;
	private View mImageView;
	private TextView mLoginStatusMessageView;
	private TextView mForgotPasswordView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.activity_login);

		// Set up the login form.
		mEmail = "stefano.candori";
		mEmailView = (EditText) findViewById(R.id.email);
		mEmailView.setText(mEmail);

		mPasswordView = (EditText) findViewById(R.id.password);
		mPasswordView
				.setOnEditorActionListener(new TextView.OnEditorActionListener() {
					@Override
					public boolean onEditorAction(TextView textView, int id,
							KeyEvent keyEvent) {
						if (id == R.id.login || id == EditorInfo.IME_NULL) {
							attemptLogin();
							return true;
						}
						return false;
					}
				});

		mLoginFormView = findViewById(R.id.login_form);
		mLoginStatusView = findViewById(R.id.login_status);
		mLoginStatusMessageView = (TextView) findViewById(R.id.login_status_message);
		mForgotPasswordView = (TextView) findViewById(R.id.forgot_password_label);
		mImageView = findViewById(R.id.click_logo_image);

		findViewById(R.id.sign_in_button).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
						imm.hideSoftInputFromWindow(
								mLoginFormView.getWindowToken(), 0);
						attemptLogin();
					}
				});

		mForgotPasswordView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri
						.parse("http://www.contobancopostaclick.it/password.shtml")));
			}
		});
	}

	/**
	 * Attempts to sign in or register the account specified by the login form.
	 * If there are form errors (invalid email, missing fields, etc.), the
	 * errors are presented and no actual login attempt is made.
	 */
	public void attemptLogin() {
		if (mAuthTask != null) {
			return;
		}

		// Reset errors.
		mEmailView.setError(null);
		mPasswordView.setError(null);

		// Store values at the time of the login attempt.
		mEmail = mEmailView.getText().toString();
		mPassword = mPasswordView.getText().toString();

		boolean cancel = false;
		View focusView = null;

		// Check for a valid password.
		// TODO uncomment this
		// if (TextUtils.isEmpty(mPassword)) {
		// mPasswordView.setError(getString(R.string.error_field_required));
		// focusView = mPasswordView;
		// cancel = true;
		// }

		// Check for a valid email address.
		if (TextUtils.isEmpty(mEmail)) {
			mEmailView.setError(getString(R.string.error_field_required));
			focusView = mEmailView;
			cancel = true;
		}

		if (cancel) {
			// There was an error; don't attempt login and focus the first
			// form field with an error.
			focusView.requestFocus();
		} else {
			// Show a progress spinner, and kick off a background task to
			// perform the user login attempt.
			mLoginStatusMessageView.setText(R.string.login_progress_signing_in);

			showProgress(true);
			mAuthTask = new UserLoginTask();
			mAuthTask.execute((Void) null);
		}
	}

	/**
	 * Shows the progress UI and hides the login form.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	private void showProgress(final boolean show) {
		// On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
		// for very easy animations. If available, use these APIs to fade-in
		// the progress spinner.
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			int shortAnimTime = getResources().getInteger(
					android.R.integer.config_shortAnimTime);

			mLoginStatusView.setVisibility(View.VISIBLE);
			mLoginStatusView.animate().setDuration(shortAnimTime)
					.alpha(show ? 1 : 0)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mLoginStatusView.setVisibility(show ? View.VISIBLE
									: View.GONE);
						}
					});

			mLoginFormView.setVisibility(View.VISIBLE);
			mLoginFormView.animate().setDuration(shortAnimTime)
					.alpha(show ? 0 : 1)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mLoginFormView.setVisibility(show ? View.GONE
									: View.VISIBLE);
						}
					});

			mImageView.setVisibility(View.VISIBLE);
			mImageView.animate().setDuration(shortAnimTime).alpha(show ? 0 : 1)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mImageView.setVisibility(show ? View.GONE
									: View.VISIBLE);
						}
					});
		} else {
			// The ViewPropertyAnimator APIs are not available, so simply show
			// and hide the relevant UI components.
			mLoginStatusView.setVisibility(show ? View.VISIBLE : View.GONE);
			mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
			mImageView.setVisibility(show ? View.GONE : View.VISIBLE);
		}
	}

	/**
	 * Represents an asynchronous login/registration task used to authenticate
	 * the user.
	 */
	public class UserLoginTask extends AsyncTask<Void, Void, String> {
		private Boolean flagError;

		@Override
		protected String doInBackground(Void... params) {
			flagError = false;
			return "100 €;10.5 €;12345678;Stefano Candori;1500.50;345.48";
			// return executeRequest();
		}

		private String executeRequest() {
			String contabile = "";
			String disponibile = "";
			String numeroConto = "";
			String intestatario = "";

			DefaultHttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(
					"https://bancopostaclick.poste.it/cweb/bancoposta/logon.fcc");

			try {
				// Add your data
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(
						2);
				nameValuePairs.add(new BasicNameValuePair("USER", mEmail));
				mPassword = "";
				nameValuePairs
						.add(new BasicNameValuePair("PASSWORD", mPassword));
				httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

				// Execute HTTP Post Request
				HttpResponse response = httpclient.execute(httppost);

				HttpGet httpget = new HttpGet(
						"https://bancopostaclick.poste.it/cweb/bancoposta/servizi/ListaMovimenti/ListaMovimenti.aspx");
				response = httpclient.execute(httpget);
				HttpEntity entity = response.getEntity();
				InputStream is = entity.getContent();

				HtmlCleaner cleaner = new HtmlCleaner();
				CleanerProperties props = cleaner.getProperties();
				props.setAllowHtmlInsideAttributes(true);
				props.setAllowMultiWordAttributes(true);
				props.setRecognizeUnicodeChars(true);
				props.setOmitComments(true);

				TagNode node = cleaner.clean(new InputStreamReader(is));

				String result = "";

				result = findInfo(node,
						"//*[@id=\"Listamovimenti_Lista_lblSaldoContabile\"]");
				if (result.length() > 0) {
					contabile = result;
				} else {
					flagError = true;
					return "Error!Saldo Contabile not found";
				}

				result = findInfo(node,
						"//*[@id=\"Listamovimenti_Lista_lblSaldoDisponibile\"]");
				if (result.length() > 0) {
					disponibile = result;
				} else {
					flagError = true;
					return "Error!Saldo Disponibile not found";
				}

				result = findInfo(node,
						"//*[@id=\"Listamovimenti_Lista_lblNumeroConto\"]");
				if (result.length() > 0) {
					numeroConto = result;
				} else {
					flagError = true;
					return "Error!Numero conto not found";
				}

				result = findInfo(node,
						"//*[@id=\"Listamovimenti_Lista_lblIntestatari\"]");
				if (result.length() > 0) {
					intestatario = result;
				} else {
					flagError = true;
					return "Error!Numero conto not found";
				}

				// Close connection
				is.close();

				return contabile + ";" + disponibile + ";" + numeroConto + ";"
						+ intestatario;

			} catch (ClientProtocolException e) {
				e.printStackTrace();
				flagError = true;
				return "Unable to execute the operation.";
			} catch (IOException e) {
				e.printStackTrace();
				flagError = true;
				return "Unable to execute the operation.";
			}
		}

		private String findInfo(TagNode node, String xpath) {
			Object[] objects;
			try {
				objects = node.evaluateXPath(xpath);
			} catch (XPatherException e) {
				e.printStackTrace();
				return "";
			}
			if (objects.length > 0) {
				return ((TagNode) objects[0]).getText().toString();
			} else {
				return "";
			}
		}

		@Override
		protected void onPostExecute(final String result) {
			mAuthTask = null;
			showProgress(false);
			if (!flagError) {
				Intent intent = new Intent(LoginActivity.this,
						MainActivity.class);
				Bundle b = new Bundle();
				b.putCharSequence("contabile", result.split(";")[0]);
				b.putCharSequence("disponibile", result.split(";")[1]);
				b.putCharSequence("numeroConto", result.split(";")[2]);
				b.putCharSequence("intestatario", result.split(";")[3]);
				b.putDouble("entrate", Double.parseDouble(result.split(";")[4]));
				b.putDouble("uscite", Double.parseDouble(result.split(";")[5]));
				intent.putExtras(b);
				startActivity(intent);

				finish();
			} else {
				mPasswordView
						.setError(getString(R.string.error_incorrect_password));
				mPasswordView.requestFocus();
			}
		}

		@Override
		protected void onCancelled() {
			mAuthTask = null;
			showProgress(false);
		}
	}
}
