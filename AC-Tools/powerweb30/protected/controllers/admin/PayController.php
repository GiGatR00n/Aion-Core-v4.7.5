<?php







class PayController extends Controller
{
	public $layout='//content';
	
	
	public function actionIndex()
	{
		if (Yii::app()->user->isGuest OR Yii::app()->user->access_level < Config::get('access_level_admin')) {
			$this->redirect(Yii::app()->homeUrl);
		}
		
		$this->pageTitle = Yii::t('title', 'Payment configuration');
		
		$model = SettingsPay::model()->find();
		
		if(isset($_POST['SettingsPay']))
		{
			$model->attributes = $_POST['SettingsPay'];
			
			if ($model->save())
			{
				Yii::app()->user->setFlash('message', '<div class="flash_success">'.Yii::t('settings', 'Payment configuration saved!').'</div>');
				$this->refresh();
			}
		}
		
		$this->render('/admin/pay', array(
			'model' => $model,
		));
	}

}