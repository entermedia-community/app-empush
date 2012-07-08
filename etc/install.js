importPackage( Packages.com.openedit.util );
importPackage( Packages.java.util );
importPackage( Packages.java.lang );
importPackage( Packages.com.openedit.modules.update );

var appname = "app-empush";
var foldername = "empush";

//http://dev.entermediasoftware.com/jenkins/job/app-emshare/lastSuccessfulBuild/artifact/deploy/app-emshare.zip
var zip = "http://dev.entermediasoftware.com/jenkins/job/@BRANCH@" + appname + "/lastSuccessfulBuild/artifact/deploy/" + appname + ".zip";

var root = moduleManager.getBean("root").getAbsolutePath();
var tmp = root + "/WEB-INF/tmp";

log.add("1. GET THE LATEST ZIP FILE for " + appname);
var downloader = new Downloader();
downloader.download( zip, tmp + "/" + appname + ".zip");


var files = new FileUtils();
files.deleteAll( root + "/WEB-INF/base/" + foldername);


log.add("2. UNZIP WAR FILE");
var unziper = new ZipUtil();
unziper.unzip(  tmp + "/" + appname + ".zip",  root );

log.add("3. REPLACE LIBS");
var files = new FileUtils();
files.deleteMatch( web + "/lib/app-empush*.jar");

files.copyFileByMatch( tmp + "/lib/app-empush*.jar", web + "/lib/");


files.deleteMatch( web + "/WEB-INF/base/" + appname + "/")

files.copyFileByMatch( tmp + "/base/empush/", root + "/WEB-INF/base/empush/");

log.add("4. CLEAN UP");
files.deleteAll(tmp);

log.add("5. UPGRADE COMPLETED");
