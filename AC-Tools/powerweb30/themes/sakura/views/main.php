<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ru" lang="ru">
<head>
	<title><?php echo CHtml::encode($this->pageTitle); ?> - <?php echo CHtml::encode(Yii::app()->name); ?></title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="language" content="ru" />
	
	<link rel="shortcut icon" href="<?php echo Yii::app()->theme->baseUrl; ?>images/favicon.png" type="image/x-icon" />
	<link rel="stylesheet" href="<?php echo Yii::app()->theme->baseUrl; ?>css/style.css" type="text/css"  />
	<link rel="stylesheet" href="<?php echo Yii::app()->theme->baseUrl; ?>css/modules.css" type="text/css" />
	<link rel="stylesheet" href="<?php echo Yii::app()->homeUrl; ?>js/redmond/jquery-ui-1.8.17.custom.css" type="text/css" />
	
	<script type="text/javascript" src="<?php echo Yii::app()->homeUrl; ?>js/aiondatabase.js"></script>
	
	<script type="text/javascript" src="<?php echo Yii::app()->theme->baseUrl; ?>js/cufon-yui.js"></script>
	<script type="text/javascript" src="<?php echo Yii::app()->theme->baseUrl; ?>js/Consolas_italic_700.font.js"></script>

	<script type="text/javascript" src="<?php echo Yii::app()->homeUrl; ?>js/jquery.js"></script>
	<script type="text/javascript" src="<?php echo Yii::app()->homeUrl; ?>js/jquery_ui.js"></script>
	<script type="text/javascript" src="<?php echo Yii::app()->homeUrl; ?>js/jquery.qtip.js"></script>
	<script type="text/javascript" src="<?php echo Yii::app()->homeUrl; ?>js/jquery.cookie.js"></script>
	<script type="text/javascript" src="<?php echo Yii::app()->homeUrl; ?>js/highcharts/highcharts.js"></script>
	
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
	
	<script type="text/javascript">
		Cufon.replace('.note_title', {color: '#444', textShadow: '#fff 1px 1px'});
	</script>
</head>
<body>

<div id="navigation">
	<ul>
	<li><a href="<?php echo Yii::app()->homeUrl; ?>">Home</a></li>
	<li><a href="<?php echo Yii::app()->homeUrl; ?>online">Online</a></li>
	<li><a href="<?php echo Yii::app()->homeUrl; ?>top">Тоp Players</a></li>
	<li><a href="<?php echo Yii::app()->homeUrl; ?>abyss">Тоp Abys</a></li>
	<li><a href="<?php echo Yii::app()->homeUrl; ?>legion">Тоp Legions</a></li>
	<li><a href="<?php echo Yii::app()->homeUrl; ?>castle">Abys</a></li>
	<li><a href="<?php echo Yii::app()->homeUrl; ?>stats">Statistics</a></li>
	<li><a href="http://makeserv.net/" title="PowerWeb - Веб-обвязка Aion">Форум</a></li>
	<?php if(!Yii::app()->user->isGuest): ?><li><a href="<?php echo Yii::app()->homeUrl; ?>logout"/>LogOut</a></li><?php endif; ?>
	</ul>
</div>
<!-- navigation -->

<div id="header">
</div>
<!-- header -->

<div id="logo">
	<a href="<?php echo Yii::app()->homeUrl; ?>"><img src="<?php echo Yii::app()->theme->baseUrl; ?>images/logo.png" title="PowerWeb - Aion" /></a>
</div>
<!-- logo -->


<div id="about">
	<div class="about_t"></div>
	<div class="about">
		Nullam in ultricies leo. Vestibulum id nisl in felis commodo auctor. Ut sed suscipit massa. Pellentesque sit amet nulla augue. Etiam id magna quam, ac sagittis sem. Ut neque nunc, elementum at fermentum non, consectetur a nisl. Nam sit amet felis velit.
	</div>
	<div class="about_b"></div>
</div>
<!-- about -->


<div id="wrapper">

	<div id="content">
		<?php $this->widget('application.components.WidgetPvp'); ?>
		
		<?php echo $content; ?>
	</div>
	<!-- content -->
	
	<div id="sidebar">
		<div class="menu">
			<div class="menu_title">Navigator</div>
			<div class="menu_body">
				<ul class="menu_link">
				<li><a href="<?php echo Yii::app()->homeUrl; ?>">Home</a></li>
				<li><a href="<?php echo Yii::app()->homeUrl; ?>register">Register</a></li>
				<li><a href="<?php echo Yii::app()->homeUrl; ?>online">Online</a></li>
				<li><a href="<?php echo Yii::app()->homeUrl; ?>top">Top Players</a></li>
				<li><a href="<?php echo Yii::app()->homeUrl; ?>abyss">Тоp Abys</a></li>
				<li><a href="<?php echo Yii::app()->homeUrl; ?>legion">Тop Legions</a></li>
				<li><a href="<?php echo Yii::app()->homeUrl; ?>castle">Siege</a></li>
				<li><a href="<?php echo Yii::app()->homeUrl; ?>search">Search</a></li>
				<li><a href="<?php echo Yii::app()->homeUrl; ?>droplist">Droplist</a></li>
				<li><a href="<?php echo Yii::app()->homeUrl; ?>broker">Broker</a></li>
				<li><a href="<?php echo Yii::app()->homeUrl; ?>stats">Statistics</a></li>
				</ul>
			</div>
		</div>
		<div class="menu_b"></div>
		
		<div class="menu">
			<div class="menu_title">Admin</div>
			<div class="menu_body">
				<?php if(Yii::app()->user->isGuest): ?>
					<?php $this->widget('application.components.WidgetLogin'); ?>
				<?php else: ?>
					<ul class="menu_link">
					<li>Вы вошли как <b><?php echo Yii::app()->user->name; ?></b></li>
					<?php if (Yii::app()->user->access_level >= Config::get('access_level_editor')): ?><li><a href="<?php echo Yii::app()->homeUrl; ?>admin">Аdministrations</a></li><?php endif; ?>
					<li><a href="<?php echo Yii::app()->homeUrl; ?>account">Аccount</a></li>
					<li><a href="<?php echo Yii::app()->homeUrl; ?>pers">Players</a></li>
					<li><a href="<?php echo Yii::app()->homeUrl; ?>webshop">Webshop</a></li>
					<li><a href="<?php echo Yii::app()->homeUrl; ?>userbar">Userbar</a></li>
					<li><a href="<?php echo Yii::app()->homeUrl; ?>settings">Settings</a></li>
					<li><a href="<?php echo Yii::app()->homeUrl; ?>logout">Logout</a></li>
					</ul>
				<?php endif; ?>
			</div>
		</div>
		<div class="menu_b"></div>
		
		<div class="menu">
			<div class="menu_title">Server Status</div>
			<div class="menu_body">
				<div class="menu_line">Login: <b><?php echo Status::server('127.0.0.1', 80); ?></b></div>
				<div class="menu_line">Game: <b><?php echo Status::server('127.0.0.1', 80); ?></b></div>
		        <div class="menu_line">Chat: <b><?php echo Status::server('127.0.0.1', 80); ?></b></div>
				<div class="menu_line">GeoData: <b><?php echo Status::server('127.0.0.1', 80); ?></b></div>
		        <div class="menu_line">TeamSpeak3: <b><?php echo Status::server('127.0.0.1', 80); ?></b></div>
			</div>
		</div>
		<div class="menu_b"></div>
		
		<div class="menu">
			<div class="menu_title">Server Status</div>
			<div class="menu_body">
				
				<div class="menu_line">Online: <b><?php echo Status::online(789); ?></b></div>
				<div class="menu_line">Asmodians: <b><?php echo Status::online_asmo(); ?></b></div>
				<div class="menu_line">Elyos: <b><?php echo Status::online_ely(); ?></b></div>
			</div>
		</div>
		<div class="menu_b"></div>
		
		<div class="menu">
			<div class="menu_title">ГМ-ы онлайн</div>
			<div class="menu_body">
				<?php $this->widget('application.components.WidgetGm'); ?>
			</div>
		</div>
		<div class="menu_b"></div>
		
		<div class="menu">
			<div class="menu_title">Голосование</div>
			<div class="menu_body">
				<div class="center mb10">
					<a href="#"><img src="<?php echo Yii::app()->theme->baseUrl; ?>images/banner_mmotop.png" /></a>
					<a href="#"><img src="<?php echo Yii::app()->theme->baseUrl; ?>images/banner_l2top.png" /></a>
				</div>
				<div class="center mb10">
					<a href="#"><img src="<?php echo Yii::app()->theme->baseUrl; ?>images/banner_aiontop.gif" /></a>
					<a href="#"><img src="<?php echo Yii::app()->theme->baseUrl; ?>images/banner_wartop.gif" /></a>
				</div>
				<div class="center">
					<a href="<?php echo Yii::app()->homeUrl; ?>vote/gtop/"><img src="<?php echo Yii::app()->theme->baseUrl; ?>images/banner_gtop100.jpg" /></a>
					<a href="<?php echo Yii::app()->homeUrl; ?>vote/gamesites/"><img src="<?php echo Yii::app()->theme->baseUrl; ?>images/banner_gamesites200.png" /></a>
				</div>
				<div class="center">
					<a href="<?php echo Yii::app()->homeUrl; ?>vote/xtremetop/"><img src="<?php echo Yii::app()->theme->baseUrl; ?>images/banner_xtremetop100.png" /></a>
				</div>
			</div>
		</div>
		<div class="menu_b"></div>
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