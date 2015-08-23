<?php







class CheckController extends Controller
{
	public $layout='//content';
	
	
	public function actionItems()
	{
		if (Yii::app()->user->isGuest OR Yii::app()->user->access_level < Config::get('access_level_admin')) {
			$this->redirect(Yii::app()->homeUrl);
		}
		
		$this->pageTitle = Yii::t('title', 'Checking fused items...');
		
		$criteria=new CDbCriteria;
		$criteria->select = 'item_unique_id, item_id, item_owner, fusioned_item';
		$criteria->condition = 'fusioned_item NOT LIKE "100%" AND fusioned_item NOT LIKE "101%" AND fusioned_item NOT LIKE "0" OR fusioned_item LIKE "1000*" OR fusioned_item LIKE "1001*" OR fusioned_item LIKE "1002*"';
		$model = Inventory::model()->with('players')->findAll($criteria);
		
		$this->render('/admin/check_items', array(
			'model' => $model,
		));
	}
	
	
	public function actionIdelete($id)
	{
		if (Yii::app()->user->isGuest OR Yii::app()->user->access_level < Config::get('access_level_admin')) {
			$this->redirect(Yii::app()->homeUrl);
		}
		
		$model = Inventory::model()->findByPK($id);
		$model->delete();
		$this->redirect(Yii::app()->homeUrl.'admin/check/items/');
	}
	
	
	public function actionQuest()
	{
		if (Yii::app()->user->isGuest OR Yii::app()->user->access_level < Config::get('access_level_admin')) {
			$this->redirect(Yii::app()->homeUrl);
		}
		
		$this->pageTitle = Yii::t('title', 'Checking quests...');
		
		$criteria=new CDbCriteria;
		$criteria->select = 'quest_id, complete_count';
		$criteria->condition = 'complete_count > 255';
		$model = PlayerQuests::model()->with('players')->findAll($criteria);
		
		$this->render('/admin/check_quest', array(
			'model' => $model,
		));
	}
	
	
	public function actionExpire()
	{
		if (Yii::app()->user->isGuest OR Yii::app()->user->access_level < Config::get('access_level_admin')) {
			$this->redirect(Yii::app()->homeUrl);
		}
		
		$this->pageTitle = Yii::t('title', 'Removing expired privileges...');
		
		$today = date("Y-m-d");
		
		$model = AccountData::model()->findAll('expire < "'.$today.'"');
		
		if (isset($_POST['Clear']))
		{
			$model = Yii::app()->ls->createCommand()
			->update('account_data', array(
				'membership'=>'0',
				'expire'=> null,
			), 'expire < "'.$today.'"');
			
			Yii::app()->user->setFlash('message', '<div class="flash_success">'.Yii::t('main', 'Expired privileges removed!').'</div>');
			$this->refresh();
		}
		
		$this->render('/admin/check_expire', array(
			'model' => $model,
		));
	}


}