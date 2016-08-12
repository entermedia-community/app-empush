package org.openedit.entermedia.push;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.entermediadb.asset.Asset;
import org.entermediadb.asset.MediaArchive;
import org.entermediadb.asset.push.PushManager;
import org.entermediadb.asset.scanner.AssetImporter;
public class PushingAssetImporter extends AssetImporter
{
	private static final Log log = LogFactory.getLog(PushingAssetImporter.class);

	//When a new asset is found we push it
	//If we cant put now then the PushManager can run on a timmer to capture non-error assets?
	
	protected PushManager fieldPushManager;

	public PushManager getPushManager()
	{
		return fieldPushManager;
	}

	public void setPushManager(PushManager inPushManager)
	{
		fieldPushManager = inPushManager;
	}
	protected void assetsImported( MediaArchive inArchive, java.util.List<Asset> inAssetsSaved)
	{
		getPushManager().pushAssets(inArchive, inAssetsSaved);
	};
}
