<?php

return array(
	'homeUrl'=>'http://www.aion-core.net/', // Homepage URL, the slash at the end is required
	'basePath'=>dirname(__FILE__).DIRECTORY_SEPARATOR.'..',
	'name'=>'PowerWeb', // Can be customised
	'language' => 'en', // There is only a language available "en" English
	'theme' => 'classic/', // Name of the themes folder being used
	
	
	'preload'=>array('log'),
	'import'=>array(
		'application.models.*',
		'application.components.*',
	),
	'defaultController'=>'news',
	'modules'=>array(
		/*'gii'=>array(
			'class'=>'system.gii.GiiModule',
			'password'=>'aaa',
			'ipFilters'=>array('127.0.0.1','::1'),
		),*/
	),
	'components'=>array(
		'user'=>array(
			'allowAutoLogin'=>true,
		),
		'ih'=>array(
			'class'=>'CImageHandler',
		),
		'urlManager'=>array(
			'urlFormat'=>'path',
			'showScriptName'=>false,
			'rules'=>require(dirname(__FILE__).'/routes.php'),
		),
		
		// Cache
		//'cache'=>array(
		//	'class'=>'CDbCache',
		//),
		
		// Database connection settings
		'db'=>array(
			'connectionString' => 'mysql:host=localhost;dbname=pow', // Host&Database connection string
			'emulatePrepare' => true,
			'username' => 'root', // Database Username
			'password' => 'MyPass', // Database Password
			'charset' => 'utf8',
			'enableProfiling' => true,
			'enableParamLogging'=>true,
			//'schemaCachingDuration'=>3600, // cache time, sec
		),
		'gs'=>array(
			'class'=>'CDbConnection',
			'connectionString' => 'mysql:host=localhost;dbname=ac47_server_gs', // Host&Database gameserver connection string
			'emulatePrepare' => true,
			'username' => 'root', // Database Username
			'password' => 'MyPass', // Database Password
			'charset' => 'utf8',
			'enableProfiling' => true,
			'enableParamLogging'=>true,
			//'schemaCachingDuration'=>3600, // cache time, sec
		),
		// Логинсервер
		'ls'=>array(
			'class'=>'CDbConnection',
			'connectionString' => 'mysql:host=localhost;dbname=ac47_server_ls', // Host&Database loginserver connection string
			'emulatePrepare' => true,
			'username' => 'root',
			'password' => 'MyPass',
			'charset' => 'utf8',
			'enableProfiling' => true,
			'enableParamLogging'=>true,
			//'schemaCachingDuration'=>3600, // cache time, sec
		),
		'session' => array(
			'class' => 'application.components.MyCDbHttpSession',
			'connectionID' => 'db',
			'sessionTableName'  =>  'session',
			'autoCreateSessionTable' => false,
			'compareIpAddress'=>true,
			'compareUserAgent'=>true,
			'compareIpBlocks'=>0
		),
		
		'errorHandler'=>array(
            'errorAction'=>'site/error',
        ),
		
		'log'=>array(
			'class'=>'CLogRouter',
			'routes'=>array(
				array(
					'class'=>'CFileLogRoute',
					'levels'=>'error, warning',
					//'class'=>'CProfileLogRoute',  // Log
					//'report'=>'summary', // Log
				),
				/* Log
				array(
					'class'=>'CWebLogRoute',
				),
				*/
			),
		),
	),
	
	
	// Прочие настройки
	'params'=>array(
		'adminEmail' =>		'mail@localhost',	// Admin email address
		'money' =>			'toll',		// Database column for donations
		'version' =>		'3.0',
	),
);