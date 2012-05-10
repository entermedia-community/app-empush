importPackage( Packages.com.openedit.util );
importPackage( Packages.java.util );
importPackage( Packages.java.lang );
importPackage( Packages.com.openedit.modules.update );

var appname = "app-empush";
var foldername = "empush";

//http://dev.entermediasoftware.com/jenkins/job/app-emshare/lastSuccessfulBuild/artifact/deploy/app-emshare.zip
var zip = "http://dev.entermediasoftware.com/jenkins/job/" + appname + "/lastSuccessfulBuild/artifact/deploy/" + appname + ".zip";

var root = moduleManager.getBean("root").getAbsolutePath();
var tmp = root + "/WEB-INF/tmp";

log.add("1. GET THE LATEST ZIP FILE for " + appname);
var downloader = new Downloader();
downloader.download( zip, tmp + "/" + appname + ".zip");

files.deleteAll( root + "/WEB-INF/base/" + foldername);

log.add("2. UNZIP WAR FILE");
var unziper = new ZipUtil();
unziper.unzip(  tmp + "/" + appname + ".zip",  root );


log.add("3. CLEAN UP");
var files = new FileUtils();
files.deleteAll(tmp);

log.add("5. UPGRADE COMPLETED");
