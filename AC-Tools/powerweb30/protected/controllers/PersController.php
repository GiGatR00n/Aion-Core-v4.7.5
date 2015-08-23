<?php







class PersController extends Controller
{
	public $layout='//content';
	
	
	protected function performAjaxValidation($model) {
		if(isset($_POST['ajax']) && $_POST['ajax']==='user-form') {
			echo CActiveForm::validate($model);
			Yii::app()->end();
		}
	}


	public function actionIndex()
	{
		if (Yii::app()->user->isGuest) $this->redirect(Yii::app()->homeUrl);
		
		$this->pageTitle = Yii::t('title', 'Character list');
		
		$criteria=new CDbCriteria;
		$criteria->select = 'name, account_id, account_name, exp, race, player_class, creation_date';
		$criteria->join = 'INNER JOIN '.Config::db('db').'.log_referals ON (log_referals.slave_id = t.account_id AND log_referals.master_id = "'.Yii::app()->user->id.'" AND status = "unpaid")';
		$referals = Players::model()->findAll($criteria);
		
		$form = new LogReferals;
		
		if(isset($_POST['LogReferals']))
		{
			$form->attributes = $_POST['LogReferals'];
			
			$check_isset = LogReferals::model()->count('master_id = '.Yii::app()->user->id.' AND slave_id = '.$form->slave_id.' AND status = "unpaid"');
			if ($check_isset != 1) {
				Yii::app()->user->setFlash('message', '<div class="flash_error">'.Yii::t('pers', 'You have no referrаls.').'</div>');
				$this->refresh();
			}
			
			$master = AccountData::model()->find('id = '.Yii::app()->user->id);
			$check_ip = AccountData::model()->count('id = '.$form->slave_id.' AND last_ip = "'.$master->last_ip.'"');
			if ($check_ip != 0) {
				$log = LogReferals::model()->find('master_id = '.Yii::app()->user->id.' AND slave_id = '.$form->slave_id.' AND status = "unpaid"');
				$log->status = 'blocked';
				$log->update(false);
				
				Yii::app()->user->setFlash('message', '<div class="flash_error">'.Yii::t('pers', 'You have no referrаls.').'</div>');
				$this->refresh();
			}
			
			$criteria=new CDbCriteria;
			$criteria->select = 'exp';
			$criteria->condition = 'account_id = '.$form->slave_id;
			$criteria->order = 'exp DESC';
			$criteria->limit = 1;
			$check_lvl = Players::model()->find($criteria);
			if (Info::lvl($check_lvl->exp) < Config::get('referal_level')) {
				Yii::app()->user->setFlash('message', '<div class="flash_error">'.Yii::t('pers', 'Less than the minimum level.').'</div>');
				$this->refresh();
			}
			
			$criteria = new CDbCriteria;
			$criteria->condition = 'id = '.Yii::app()->user->id;
			$money = AccountData::model()->find($criteria);
			$money[Yii::app()->params->money] = $money[Yii::app()->params->money] + Config::get('referal_bonus');
			$money->save();
			
			$criteria = new CDbCriteria;
			$criteria->condition = 'id = '.$form->slave_id;
			$money = AccountData::model()->find($criteria);
			$money[Yii::app()->params->money] = $money[Yii::app()->params->money] + Config::get('referal_bonus_ref');
			$money->save();
			
			$log = LogReferals::model()->find('master_id = '.Yii::app()->user->id.' AND slave_id = '.$form->slave_id.' AND status = "unpaid"');
			$log->status = 'complete';
			$log->update(false);
			
			Yii::app()->user->setFlash('message', '<div class="flash_success">'.Yii::t('pers', 'Bonus credit applied!').'</div>');
			$this->refresh();
		}
		
		$this->render('/pers', array(
			'model' => Players::getPlayers(),
			'referals' => $referals,
		));
	}
	
}