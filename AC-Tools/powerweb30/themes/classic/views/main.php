<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ru" lang="ru">
<head>
	<title><?php echo CHtml::encode($this->pageTitle); ?> - <?php echo CHtml::encode(Yii::app()->name); ?></title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="language" content="ru" />
	
	<link rel="shortcut icon" href="<?php echo Yii::app()->theme->baseUrl; ?>/images/favicon.png" type="image/x-icon" />
	<link rel="stylesheet" href="<?php echo Yii::app()->theme->baseUrl; ?>/css/style.css" type="text/css"  />
	<link rel="stylesheet" href="<?php echo Yii::app()->theme->baseUrl; ?>/css/modules.css" type="text/css" />
	<link rel="stylesheet" href="<?php echo Yii::app()->homeUrl; ?>/js/redmond/jquery-ui-1.8.17.custom.css" type="text/css" />
	
	<script type="text/javascript" src="<?php echo Yii::app()->homeUrl; ?>/js/aiondatabase.js"></script>
	
	<script type="text/javascript" src="<?php echo Yii::app()->homeUrl; ?>/js/jquery.js"></script>
	<script type="text/javascript" src="<?php echo Yii::app()->homeUrl; ?>/js/jquery_ui.js"></script>
	<script type="text/javascript" src="<?php echo Yii::app()->homeUrl; ?>/js/jquery.qtip.js"></script>
	<script type="text/javascript" src="<?php echo Yii::app()->homeUrl; ?>/js/jquery.cookie.js"></script>
	<script type="text/javascript" src="<?php echo Yii::app()->homeUrl; ?>/js/highcharts/highcharts.js"></script>
	
	<script type="text/javascript">
	$(document).ready(function() {
		$('*[title]').qtip({
			content: {text: false},
			style: 'dark',
			position: {
				target: 'mouse',
				adjust: {screen: true}
			}
		});  
	});
	</script>
</head>
<body>

<div id="navigation">
	<ul>
	<li><a href="<?php echo Yii::app()->homeUrl; ?>">Homepage</a></li>
	<li><a href="<?php echo Yii::app()->homeUrl; ?>online">Players online</a></li>
	<li><a href="<?php echo Yii::app()->homeUrl; ?>top">Top players</a></li>
	<li><a href="<?php echo Yii::app()->homeUrl; ?>abyss">Top Abyss players</a></li>
	<li><a href="<?php echo Yii::app()->homeUrl; ?>legion">Top legion</a></li>
	<li><a href="<?php echo Yii::app()->homeUrl; ?>castle">Fortress sieges</a></li>
	<li><a href="<?php echo Yii::app()->homeUrl; ?>stats">Statistics</a></li>
	<li><a href="#" title="PowerWeb - Aion Website">Forum</a></li> <!-- Put your forum address here -fallenfate -->
	<?php if(!Yii::app()->user->isGuest): ?><li><a href="<?php echo Yii::app()->homeUrl; ?>logout"/>Log out</a></li><?php endif; ?>
	</ul>
</div>
<!-- navigation -->

<div id="header">
	<div id="logo">
		<a href="<?php echo Yii::app()->homeUrl; ?>" title="PowerWeb - Aion website"><img src="<?php echo Yii::app()->theme->baseUrl; ?>/images/logo.png" alt="logo" /></a>
	</div>
</div>
<!-- header -->

<div id="wrapper">

	<div id="sidebar_left">
		<div class="menu">
			<div class="menu_title">Menu</div>
			<div class="menu_body">
				<ul class="menu_link">
				<li><a href="<?php echo Yii::app()->homeUrl; ?>">Homepage</a></li>
				<li><a href="<?php echo Yii::app()->homeUrl; ?>online">Players online</a></li>
				<li><a href="<?php echo Yii::app()->homeUrl; ?>top">Top players</a></li>
				<li><a href="<?php echo Yii::app()->homeUrl; ?>abyss">Top Abyss players</a></li>
				<li><a href="<?php echo Yii::app()->homeUrl; ?>legion">Top legion</a></li>
				<li><a href="<?php echo Yii::app()->homeUrl; ?>castle">Fortress sieges</a></li>
				<li><a href="<?php echo Yii::app()->homeUrl; ?>search">Find a player</a></li>
				<li><a href="<?php echo Yii::app()->homeUrl; ?>droplist">Droplist</a></li>
				<li><a href="<?php echo Yii::app()->homeUrl; ?>broker">Auctions</a></li>
				<li><a href="<?php echo Yii::app()->homeUrl; ?>stats">Statistics</a></li>
				</ul>
			</div>
		</div>
		<div class="menu">
			<div class="menu_title">Account</div>
			<div class="menu_body">
				<?php if(Yii::app()->user->isGuest): ?>
					<?php $this->widget('application.components.WidgetLogin'); ?>
				<?php else: ?>
					<ul class="menu_link">
					<li>Logged in as <b><?php echo Yii::app()->user->name; ?></b></li>
					<?php if (Yii::app()->user->access_level >= Config::get('access_level_editor')): ?><li><a href="<?php echo Yii::app()->homeUrl; ?>admin">Admin area</a></li><?php endif; ?>
					<li><a href="<?php echo Yii::app()->homeUrl; ?>account">Account</a></li>
					<li><a href="<?php echo Yii::app()->homeUrl; ?>pers">Characters</a></li>
					<li><a href="<?php echo Yii::app()->homeUrl; ?>webshop">Webshop</a></li>
					<li><a href="<?php echo Yii::app()->homeUrl; ?>userbar">User area</a></li>
					<li><a href="<?php echo Yii::app()->homeUrl; ?>settings">Settings</a></li>
					<li><a href="<?php echo Yii::app()->homeUrl; ?>logout">Log out</a></li>
					</ul>
				<?php endif; ?>
			</div>
		</div>
	</div>
	<!-- sidebar -->
	
	<div id="content">
		<?php echo $content; ?>
		<?php $this->widget('application.components.WidgetPvp'); ?>
	</div>
	<!-- content -->
	
	<div id="sidebar_right">
		<div class="menu">
			<div class="menu_title">Server status</div>
			<div class="menu_body">
				<div class="menu_line">Login: <b><?php echo Status::server('127.0.0.1', 80); ?></b></div>
				<div class="menu_line">Game: <b><?php echo Status::server('127.0.0.1', 80); ?></b></div>
			    <div class="menu_line">Chat: <b><?php echo Status::server('127.0.0.1', 80); ?></b></div>
				<div class="menu_line">GeoData: <b><?php echo Status::server('127.0.0.1', 80); ?></b></div>
		        <div class="menu_line">TeamSpeak3: <b><?php echo Status::server('127.0.0.1', 80); ?></b></div>
			</div>
		</div>
		<div class="menu">
			<div class="menu_title">Online</div>
			<div class="menu_body">
				<div class="menu_line">Players online: <b><?php echo Status::online(); ?></b></div>
				<div class="menu_line">Asmodians online: <b><?php echo Status::online_asmo(); ?></b></div>
				<div class="menu_line">Elyoss online: <b><?php echo Status::online_ely(); ?></b></div>
			</div>
		</div>
		<div class="menu">
			<div class="menu_title">GMs online</div>
			<div class="menu_body">
				<?php $this->widget('application.components.WidgetGm'); ?>
			</div>
		</div>
		<div class="menu">
			<div class="menu_title">Voting</div>
			<div class="menu_body">
				<div class="center mb10">
					<a href="#"><img src="<?php echo Yii::app()->theme->baseUrl; ?>/images/banner_mmotop.png" /></a>
					<a href="#"><img src="<?php echo Yii::app()->theme->baseUrl; ?>/images/banner_l2top.png" /></a>
				</div>
				<div class="center mb10">
					<a href="#"><img src="<?php echo Yii::app()->theme->baseUrl; ?>/images/banner_aiontop.gif" /></a>
					<a href="#"><img src="<?php echo Yii::app()->theme->baseUrl; ?>/images/banner_wartop.gif" /></a>
				</div>
				<div class="center">
					<a href="<?php echo Yii::app()->homeUrl; ?>vote/gtop/"><img src="<?php echo Yii::app()->theme->baseUrl; ?>/images/banner_gtop100.jpg" /></a>
					<a href="<?php echo Yii::app()->homeUrl; ?>vote/gamesites/"><img src="<?php echo Yii::app()->theme->baseUrl; ?>/images/banner_gamesites200.png" /></a>
				</div>
				<div class="center">
					<a href="<?php echo Yii::app()->homeUrl; ?>vote/xtremetop/"><img src="<?php echo Yii::app()->theme->baseUrl; ?>/images/banner_xtremetop100.png" /></a>
				</div>
			</div>
		</div>
	</div>
	<!-- sidebar -->
	
	<div class="clear"></div>

</div>
<!-- wrapper -->

<div id="footer">
	<div class="footer">

		     <div>
	     	</div>
	  </div>
  </div>
	<!-- FOOTER -->
	<div class="center">
<div id="footer">
	<div class="footer">
		<div class="footer-block">
			<!--Credits & LinkBack - DO NOT REMOVE OR CHANGE -->
			© <a href="http://makeserv.net/topic/3629-powerweb-3-obvjazka-dlja-aion/" target="_blank" title="PowerWeb"/>PowerWeb 3.0</a> | © <a href="http://www.ncsoft.com/" target="_blank" title="NCSoft" />Aion NCSoft</a> | <br>© <a href="http://aion-core.net/" target="_blank" title="Aion-Core" />Released by Aion-Core</a> 2010-2015	
		</div>
		<div class="footer-block">
                        © <a href="http://www.yourserver.com/" target="_blank" title="Your Server Title"/>Your Aion Server Title</a> |
		</div>
			<!--Credits & LinkBack - DO NOT REMOVE OR CHANGE -->
		<div class="footer-block">
			<!--Open Links For You-->
			© <a href="http://www.yourserver.com/" target="_blank" title="Server Title"/>Aion Server Title</a> | 
			<!--Open Links For You-->
		</div>
		<div class="clear"></div>
	</div>
</div>
<!-- END FOOTER -->

</body>
</html>