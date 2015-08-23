
<?php







class StatsController extends Controller
{
	public $layout='//content';
	
	public function actionIndex()
	{
		$this->pageTitle = Yii::t('title', 'Server statistics');
		
		$this->render('/stats', array(
		));
	}


}