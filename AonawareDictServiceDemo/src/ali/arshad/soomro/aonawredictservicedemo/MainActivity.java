package ali.arshad.soomro.aonawredictservicedemo;

/** Copyright [2014-15] [Arshad Ali Soomro,
 *  http://source-code-android.blogspot.com/] Licensed under the
 *  Educational Community License, Version 2.0 (the "License"); you may
 *  not use this file except in compliance with the License. You may
 *  obtain a copy of the License at
 *  
 *  http://www.osedu.org/licenses/ECL-2.0
 * 
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an "AS IS"
 *  BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 *  or implied. See the License for the specific language governing
 *  permissions and limitations under the License. */
import ali.arshad.soomro.aonawaredistservicesdk.AonawareDictServiceWordInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {

	protected Button button = null;
	protected EditText editText = null;
	protected TextView word, defination = null;
	protected ProgressDialog progressDialog = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		button = (Button) findViewById(R.id.searchButton);
		editText = (EditText) findViewById(R.id.searchInput);
		word = (TextView) findViewById(R.id.word);
		defination = (TextView) findViewById(R.id.defination);
		String query = editText.getText().toString();
		new AsyncTask<String, Void, AonawareDictServiceWordInfo>() {
			@Override
			protected void onPreExecute() {
				progressDialog = new ProgressDialog(MainActivity.this);
				progressDialog.setTitle("Wait...");
				progressDialog.setMessage("Data is being loaded...");
				progressDialog.show();
			}

			@Override
			protected AonawareDictServiceWordInfo doInBackground(
					String... params) {
				
				return null;
			}

			@Override
			protected void onPostExecute(AonawareDictServiceWordInfo result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);
				word.setText(result.getWord());
				defination.setText(result.getWordDefination());
				progressDialog.dismiss();
			}
		}.execute(query);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
