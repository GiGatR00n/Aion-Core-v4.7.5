<?php
class Config extends Controller
{
	public function get($var)
	{
		$model = Settings::model()->find();
		return $model->$var;
    }
	
	public function db($var)
	{
		$connectionString = Yii::app()->$var->connectionString;
		preg_match('/dbname=(\w+)/', $connectionString, $matches);
		return $matches[1];
	}
	
	public function captcha()
	{
		return array(
			'captcha'=>array(
			'class'=>'CCaptchaAction',
			'maxLength'=> 4,
			'minLength'=> 4,
			'foreColor'=> 0x667e9a,
			'transparent'=> true,
			'height'=> 30,
			'width'=> 100,
			'offset'=> 5,
			'fontFile' => Yii::app()->basePath.'/fonts/romic.ttf',
			'testLimit' => 2,
			),
		);
	}
	
	public function pages($pages)
	{
		return $this->widget('CLinkPager', array(
			'pages' => $pages,
			'maxButtonCount' => 7,
			'nextPageLabel' => '&rarr;',
			'prevPageLabel' => '&larr;',
			'firstPageLabel' => 'Start',
			'lastPageLabel' => 'End',
			'header' => '',
		));
	}
	
	public function date($var)
	{
		return Yii::app()->dateFormatter->format('dd.MM.y', $var);
	}
	
	public function datetime($var)
	{
		return Yii::app()->dateFormatter->format('dd.MM.y, HH:mm:ss', $var);
	}
	
	public function Profile()
	{
		return substr(array_pop(Yii::app()->db->getStats()), 0, 5).' / '.array_shift(Yii::app()->db->getStats());
	}
	
	public function Copyright()
	{
		return 'Powered by PowerWeb '.Yii::app()->params->version;
	}
}