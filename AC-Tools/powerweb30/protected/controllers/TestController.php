<?php

class TestController extends Controller
{
	public $layout='//content';
	
	
	public function actionIndex($name)
	{
		echo $name;
	}

}