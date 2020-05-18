<%@ page import="p3.EbuyOpenHelper" %>
<!DOCTYPE html>
<html class="no-js css-menubar" lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimal-ui">


<title>Prices Paid Portal | Sign In</title><link rel="shortcut icon" href="p3/classic/center/assets/images/gsa.png">

    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

<link rel="shortcut icon" href="p3/classic/center/assets/images/gsa.png">
    <!-- Stylesheets -->
<link rel="stylesheet" href="p3/classic/global/css/bootstrap.css">
<link rel="stylesheet" href="p3/classic/global/css/bootstrap-extend.css">
<link rel="stylesheet" href="p3/classic/center/assets/css/site.css">
    <!-- Plugins -->
<link rel="stylesheet" href="p3/classic/global/vendor/animsition/animsition.css">
<link rel="stylesheet" href="p3/classic/global/vendor/asscrollable/asScrollable.css">
<link rel="stylesheet" href="p3/classic/global/vendor/switchery/switchery.css">
<link rel="stylesheet" href="p3/classic/global/vendor/intro-js/introjs.css">
<link rel="stylesheet" href="p3/classic/global/vendor/slidepanel/slidePanel.css">
<link rel="stylesheet" href="p3/classic/global/vendor/flag-icon-css/flag-icon.css">
<link rel="stylesheet" href="p3/classic/center/assets/examples/css/pages/login-v2.css">
    <!-- Fonts -->
<link rel="stylesheet" href="p3/classic/global/fonts/web-icons/web-icons.min.css">
<link rel="stylesheet" href="p3/classic/global/fonts/brand-icons/brand-icons.min.css">
<link rel='stylesheet' href='https://fonts.googleapis.com/css?family=Roboto:300,400,500,300italic'>
<!--[if lt IE 9]>
<script src="p3/classic/global/vendor/html5shiv/html5shiv.min.js"></script>
<![endif]-->
<!--[if lt IE 10]>
<script src="p3/classic/global/vendor/media-match/media.match.min.js"></script>
<script src="p3/classic/global/vendor/respond/respond.min.js"></script>
<![endif]-->
    <!-- Scripts -->
<script src="p3/classic/global/vendor/modernizr/modernizr.js"></script>
<script src="p3/classic/global/vendor/breakpoints/breakpoints.js"></script>
<script>
Breakpoints();

var gaUACode = "UA-48986383-1";

try {
	if ( window.location.hostname.indexOf( "p3-dev.cap.gsa.gov" ) >= 0 ) {
        	gaUACode = "UA-48986383-3";
        } else if ( window.location.hostname.indexOf("p3-test.cap.gsa.gov" ) >= 0 ) {
                gaUACode = "UA-48986383-4";
	} else if ( window.location.hostname.indexOf( "p3-staging.cap.gsa.gov" ) >=0 ) {
		gaUACode = "UA-48986383-5";
	}
} catch ( err ) { console.log( err ); }

(function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
    (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
        m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
})(window,document,'script','//www.google-analytics.com/analytics.js','ga');

ga('create', gaUACode, 'acquisition.gov',{'cookieDomain':'none'});
ga('send', 'pageview');



</script>
</head>

<style>
.page-dark.layout-full::before {
    background-position: left top;
}
.page-login-v2::before {
    background-image: url("p3/classic/center/assets/images/bg-login.jpeg");
}

.modal-sidebar {
    height: 800px !important;
}
</style>

<body class="page-login-v2 layout-full page-dark">
<a href="#content-skip" class="sr-only">skip to content</a>


    <!-- Page -->
<div class="page animsition" data-animsition-in="fade-in" data-animsition-out="fade-out">
<div class="page-content">
<div class="page-brand-info hidden-xs">
<div class="brand">
<img class="brand-img img-responsive" style="max-height:60px;" src="p3/classic/center/assets/images/gsa.png" alt="GSA">
<span> GENERAL SERVICES ADMINISTRATION</span><br>
<h2 class="brand-text font-size-40 font-weight-200 margin-left-0">PRICES PAID PORTAL</h2>
</div>

</div>
<div class="page-login-main">
<div class="brand visible-xs">
<img class="brand-img img-responsive" style="max-height:60px;" src="p3/classic/center/assets/images/gsa.png" alt="GSA">
<span> GENERAL SERVICES ADMINISTRATION</span><br />

<h2 class="brand-text font-size-40 font-weight-200 margin-left-0">PRICES PAID PORTAL</h2>
</div>
<h3 class="font-size-24">Sign In</h3>
<p><span class="font-size-20" >Access is limited to Federal Employees only.</span></p>

<p>Authentication is provided through MAX, please click the button <br />below to authenticate using OMB MAX.</p>




<p class="margin-top-30 margin-bottom-30"><a class="btn btn-warning btn-block" href="javascript:queryLink();">Authenticate with OMB MAX</a></p>
<p>
<a href="p3/files/Loginscreen.pdf" target="_blank">Click here for help logging in</a>
</p>
<p>To become a registered MAX user
<a href="https://max.omb.gov/maxportal/registrationForm.action" target="_blank">click here</a>. <br>
<a href="https://max.omb.gov/community/display/Egov/PIV+and+CAC+Card+Registration+Instructions" target="_blank">Instructions for registration</a>
</p>
<p><a href="mailto:p3@gsa.gov">Contact Us</a></p>
<p style="color: #37474f; font-size: 12px;">This site optimized for Chrome V43, FireFox V38, Safari V8, and IE9 and later.</p>
<br/>
<footer class="page-copyright" style="bottom:50px; width:90%;">
<p>This is a U.S. General Services Administration computer system that is<br>
"FOR OFFICIAL USE ONLY". <br>
This system is subject to monitoring. Therefore, no expectation of privacy is to be assumed. Individuals found performing unauthorized activities are subject to disciplinary action including criminal prosecution.</p>
<p>
<a data-toggle="modal" href="#behavior">View Rules of Behavior</a>
</p>
</footer>

</div>
</div>
</div>
    <!-- End Page -->


<div id="behavior" class="modal fade " tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" >
<div class="modal-dialog modal-sidebar">
<div class="modal-content">
<div class=" modal-header">
<button type="button" class="close" data-dismiss="modal" aria-hidden="true"><span class="icon wb-close"></span></button>
<h3 id="myModalLabel">Rules of Behavior</h3>
</div>
<div class="modal-body blue-grey-600" >
<p>This is a Federal computer system and is the property of the United States Government. It is for authorized use only. Unauthorized use of this system is prohibited and may constitute a violation of 18 U.S.C. C B' 1030 or other Federal laws and regulations and may result in criminal, civil, and/or administrative action.</p>
<p>Only Federal employees are authorized to use this system. It is intended for use by Federal acquisition personnel, including but not limited to procurement officials and program offices conducting market research. Contractors, even those assisting the Federal Government with an acquisition, are not authorized to use this system or otherwise obtain information from this system because it may contain contractor information considered to be company confidential. Unauthorized disclosure of company confidential information is subject to the penalties set forth in 18 U.S.C. C B' 1905 and 41 U.S.C. C B' 2105. Information downloaded or otherwise obtained from the system must be protected in accordance with this agreement and may also be considered source selection sensitive pursuant to 41 USC 2101(7) and FAR part 3 in accordance with agency procedures.</p>
<p>By accessing and continuing to use this system, you indicate your awareness of, and consent to, these terms and conditions and acknowledge that there is no reasonable expectation of privacy in the access or use of this computer system. Users (authorized or unauthorized) have no explicit or implicit expectation of privacy in anything viewed, created, downloaded, or stored on this system, including e-mail, Internet, and Intranet use. Any or all uses of this system (including all peripheral devices and output media) and all files on this system may be intercepted, monitored, read, captured, recorded, disclosed, copied, audited, and/or inspected by authorized agency personnel, the Office of Inspector General (OIG),and/or other law enforcement personnel, as well as authorized officials of other agencies. Access or use of this computer by any person, whether authorized or unauthorized, constitutes consent to such interception, monitoring, reading, capturing, recording, disclosure, copying, auditing, and/or inspection at the discretion of authorized agency personnel, law enforcement personnel (including the OIG),and/or authorized officials other agencies.</p>
</div>
</div></div>

</div>

    <!-- Core  -->
<script src="p3/classic/global/vendor/jquery/jquery.js"></script>
<script src="p3/classic/global/vendor/bootstrap/bootstrap.js"></script>
<script src="p3/classic/global/vendor/animsition/animsition.js"></script>
<script src="p3/classic/global/vendor/asscroll/jquery-asScroll.js"></script>
<script src="p3/classic/global/vendor/mousewheel/jquery.mousewheel.js"></script>
<script src="p3/classic/global/vendor/asscrollable/jquery.asScrollable.all.js"></script>
<script src="p3/classic/global/vendor/ashoverscroll/jquery-asHoverScroll.js"></script>
    <!-- Plugins -->
<script src="p3/classic/global/vendor/switchery/switchery.min.js"></script>
<script src="p3/classic/global/vendor/intro-js/intro.js"></script>
<script src="p3/classic/global/vendor/screenfull/screenfull.js"></script>
<script src="p3/classic/global/vendor/slidepanel/jquery-slidePanel.js"></script>
<script src="p3/classic/global/vendor/jquery-placeholder/jquery.placeholder.js"></script>
    <!-- Scripts -->
<script src="p3/classic/global/js/core.js"></script>
<script src="p3/classic/center/assets/js/site.js"></script>
<script src="p3/classic/center/assets/js/sections/menu.js"></script>
<script src="p3/classic/center/assets/js/sections/menubar.js"></script>
<script src="p3/classic/center/assets/js/sections/sidebar.js"></script>
<script src="p3/classic/global/js/configs/config-colors.js"></script>
<script src="p3/classic/global/js/components/asscrollable.js"></script>
<script src="p3/classic/global/js/components/animsition.js"></script>
<script src="p3/classic/global/js/components/slidepanel.js"></script>
<script src="p3/classic/global/js/components/switchery.js"></script>
<script src="p3/classic/global/js/components/jquery-placeholder.js"></script>
<script>
(function(document, window, $) {
    'use strict';
    var Site = window.Site;
    $(document).ready(function() {
        Site.run();
    });
})(document, window, jQuery);

function getParameterByName(name, url) {
    if (!url) url = window.location.href;
    name = name.replace(/[\[\]]/g, "\\$&");
    var regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)", "i"),
            results = regex.exec(url);
    if (!results) return null;
    if (!results[2]) return '';
    return decodeURIComponent(results[2].replace(/\+/g, " "));
}

function queryLink() {
    if(getParameterByName("query")) {
        location.href = "<%= EbuyOpenHelper.getOMBMaxLoginLink(request) %>" + '?query=' + getParameterByName("query");
    } else {
        location.href = "<%= EbuyOpenHelper.getOMBMaxLoginLink(request) %>";
    }
};
</script>

</body>

</html>
