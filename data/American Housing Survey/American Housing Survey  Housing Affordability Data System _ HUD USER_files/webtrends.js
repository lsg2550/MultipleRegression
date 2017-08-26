// WebTrends SmartSource Data Collector Tag v10.4.23
// Copyright (c) 2016 Webtrends Inc.  All rights reserved.
// Tag Builder Version: 4.1.3.5
// Created: 2016.01.13
window.webtrendsAsyncInit=function(){
        var test_host = new RegExp("beta.huduser.gov");
        var on_test = test_host.test(window.location);
        
	//set to Prod dcs & domain values
	var dcsid_val = "dcs222hq475ld9e3xms4vcc23_5x8e";
	var domain_val = "statse.webtrendslive.com";
	var onsitedoms_val = "huduser.gov";
	var fpcdom_val = ".huduser.gov";
	
	//check what host we're on and use appropriate dcsid & domain info
	//Check if on test domain otherwise default to prod
	 if (on_test) {
		dcsid_val = "dcs2225tb5o5s6afgkm1dvz1p_5x2p";
		domain_val = "statse.webtrendslive.com";
		onsitedoms_val = "beta.huduser.gov";
		fpcdom_val = ".beta.huduser.gov";
         }
    var dcs=new Webtrends.dcs().init({
        dcsid:dcsid_val,
        domain:domain_val,
        timezone:-5,
        i18n:false,
        adimpressions:true,
        adsparam:"WT.ac",
        offsite:true,
        download:true,
        downloadtypes:"xls,doc,pdf,txt,csv,zip,docx,xlsx,rar,gzip",
        onsitedoms:onsitedoms_val,
        fpcdom:fpcdom_val,
        plugins:{
            getcg:{src:"/portal/js/webtrends.getcg.js"},
	    sage:{src:"/portal/js/webtrends.sage.js"}
        }
        }).track();
};
(function(){
    var s=document.createElement("script"); s.async=true; s.src="//s.webtrends.com/js/webtrends.js";    
    var s2=document.getElementsByTagName("script")[0]; s2.parentNode.insertBefore(s,s2);
}());
