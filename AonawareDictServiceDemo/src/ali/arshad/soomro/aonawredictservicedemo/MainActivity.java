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
import org.xml.sax.SAXParseException;

import ali.arshad.soomro.aonawaredistservicesdk.AonawareDictServiceUtils;
import ali.arshad.soomro.aonawaredistservicesdk.AonawareDictServiceWordInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {

	protected Button button = null;
	protected EditText editText = null;
	protected TextView word, defination = null;
	protected ProgressDialog progressDialog = null;

	protected AonawareDictServiceUtils serviceUtils = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		serviceUtils = AonawareDictServiceUtils.getInstance();

		button = (Button) findViewById(R.id.searchButton);
		editText = (EditText) findViewById(R.id.searchInput);
		word = (TextView) findViewById(R.id.word);
		defination = (TextView) findViewById(R.id.defination);
		

		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
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

						try {
							return serviceUtils.queryWord(MainActivity.this, AonawareDictServiceUtils.getInstance().ID_CIDE, editText.getText().toString());
						} catch (SAXParseException e) {
							// TODO Auto-generated catch block
							Log.e("TAG", "No data recieved");
							return null;
						}
					}
					@Override
					protected void onPostExecute(AonawareDictServiceWordInfo result) {
						word.setText(result.getWord());
						defination.setText(result.getWordDefination());
						progressDialog.dismiss();
					}
				}.execute(null, null, null);

			}
		});


	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}