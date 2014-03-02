
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
package ali.arshad.soomro.aonawaredistservicesdk;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import android.content.Context;

public class AonawareDictServiceUtils {
	public static final String AONAWARE_TAG = "AONAWARE_TAG";
	protected static final String URL = "http://services.aonaware.com/DictService/DictService.asmx/Define?word=";

	public static AonawareDictServiceUtils getInstance() {
		return (new AonawareDictServiceUtils());
	}

	public AonawareDictServiceWordInfo queryWord(Context context, String word) {
		AonawareDictServiceUtils dictServiceUtils = AonawareDictServiceUtils
				.getInstance();
		String wordDef = getWordDefinition(context, word);
		if (wordDef.equals("")) {
			return null;
		} else {
			Document document = dictServiceUtils
					.convertWordDefinationToDocument(context, wordDef);
			AonawareDictServiceWordInfo dictServiceWordInfo = parseWordInfo(
					context, document);
			return dictServiceWordInfo;
		}
	}

	protected String getWordDefinition(Context context, String word) {
		StringBuilder wordDefination = new StringBuilder();
		HttpClient httpClient = new DefaultHttpClient();
		StringBuilder queryString = new StringBuilder(URL);
		queryString.append(word);
		HttpGet request = new HttpGet(queryString.toString());
		HttpResponse httpResponse;
		try {
			httpResponse = httpClient.execute(request);
			HttpEntity httpEntity = httpResponse.getEntity();
			BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(httpEntity.getContent()));
			String readLineString = "";
			while ((readLineString = bufferedReader.readLine()) != null) {
				wordDefination.append(readLineString + "\n");
			}
			return (wordDefination.toString());
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	protected Document convertWordDefinationToDocument(Context context,
			String defSource) {
		Document destDocument = null;

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = null;

		try {
			documentBuilder = factory.newDocumentBuilder();
			destDocument = documentBuilder.parse(new ByteArrayInputStream(
					defSource.getBytes()));
			return destDocument;
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
			return null;
		} catch (SAXException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}catch (Exception e) {
			return null;
		}
	}

	protected AonawareDictServiceWordInfo parseWordInfo(Context context,
			Document docSource) {
		AonawareDictServiceWordInfo dictServiceWordInfo = new AonawareDictServiceWordInfo();

		try {
			Node wordNode = docSource.getElementsByTagName("Word").item(0);

			dictServiceWordInfo.setWord(getNodeText(wordNode));
			dictServiceWordInfo.setDictId(getNodeText(docSource
					.getElementsByTagName("Id").item(0)));
			dictServiceWordInfo.setDictName(getNodeText(docSource
					.getElementsByTagName("Name").item(0)));
			dictServiceWordInfo.setWordDefination(getNodeText(docSource
					.getElementsByTagName("WordDefinition").item(1)));
			return dictServiceWordInfo;
		} catch (NullPointerException e) {
			return null;
		} catch (NoSuchMethodException e) {
			return null;
		}
	}

	protected String getNodeText(Node node) throws NoSuchMethodException {
		String string = "";
		string = node.getTextContent();

		return (string);
	}

}
