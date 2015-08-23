<?php







class SettingsController extends Controller
{
	public $layout='//content';
	
	
	public function actionIndex()
	{
		if (Yii::app()->user->isGuest OR Yii::app()->user->access_level < Config::get('access_level_admin')) {
			$this->redirect(Yii::app()->homeUrl);
		}
		
		$this->pageTitle = Yii::t('title', 'Bound settings');
		
		$form = Settings::model()->find();
		
		if(isset($_POST['Settings']))
		{
			$form->attributes = $_POST['Settings'];
			
			if (isset($_POST['Settings']['referal_enable'])) $form->referal_enable = 1;
			else $form->referal_enable = 0;
			if (isset($_POST['Settings']['trial_enable'])) $form->trial_enable = 1;
			else $form->trial_enable = 0;
			if (isset($_POST['Settings']['email_activation'])) $form->email_activation = 1;
			else $form->email_activation = 0;
			
			if ($form->save())
			{
				Yii::app()->user->setFlash('message', '<div class="flash_success">'.Yii::t('main', 'Settings saved!').'</div>');
				$this->refresh();
			}
		}
		
		$this->render('/admin/settings', array(
			'form' => $form,
		));
	}

}