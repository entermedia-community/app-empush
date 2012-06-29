package org.openedit.entermedia.push;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openedit.entermedia.Asset;
import org.openedit.entermedia.MediaArchive;
import org.openedit.entermedia.scanner.AssetImporter;
import org.openedit.entermedia.scanner.AssetPathProcessor;

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
		String enabled = inArchive.getCatalogSettingValue("push_masterswitch");
		if( "false".equals(enabled) )
		{
			log.info("Push is paused");
			return;
		}

		Collection presets = inArchive.getCatalogSettingValues("push_convertpresets");

		List tosave = new ArrayList();
		//convert then save
		for (Iterator iterator = inAssetsSaved.iterator(); iterator.hasNext();)
		{
			Asset asset = (Asset) iterator.next();
			asset.setProperty("pushstatus", "notallconverted");
			inArchive.fireMediaEvent("conversions/runconversion", null, asset);	//This will run right now, conflict?			
			getPushManager().uploadPresets(inArchive, presets, null, asset, tosave);
		}
		inArchive.getAssetSearcher().saveAllData(tosave, null);

	};
}
