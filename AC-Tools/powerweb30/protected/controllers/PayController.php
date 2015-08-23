<?php







class PayController extends Controller
{
	public function actionRoboResult()
	{
		if (!$_POST) exit();
		
		$model = SettingsPay::model()->find();
		$mrh_pass2 = $model->mrh_pass2;
		
		$out_summ = $_POST["OutSum"];
		$inv_id = $_POST["InvId"];
		$shp_account = $_POST["Shp_account"];
		$crc = $_POST["SignatureValue"];
		$crc = strtoupper($crc);
		
		$my_crc = strtoupper(md5("$out_summ:$inv_id:$mrh_pass2:Shp_account=$shp_account"));
		
		if ($my_crc !=$crc) {
		  echo 'bad sign';
		  exit();
		}
		
		$log = new LogBilling;
		$log->pay_id = $inv_id;
		$log->sum = $out_summ;
		$log->account = $shp_account;
		$log->status = 'unpaid';
		$log->system = 'Robokassa';
		$log->save(false);
		$status = LogBilling::model()->findByPK($inv_id);
		
		if ($status->status == 'unpaid')
		{
			$criteria = new CDbCriteria;
			$criteria->condition = 'name = "'.$shp_account.'"';
			$money = AccountData::model()->find($criteria);
			$money[Yii::app()->params->money] = $money[Yii::app()->params->money] + $out_summ;
			$money->save();
			
			$log = LogBilling::model()->findByPK($inv_id);
			$log->status = 'complete';
			$log->save(false);
			
			echo 'success';
			exit();
		}
		else {
			echo 'fail';
			exit();
		}
		
		exit();
	}
	
	public function actionRoboSuccess()
	{
		$this->redirect(Yii::app()->homeUrl.'account');
	}
	
	
	public function actionRoboFail()
	{
		if (!$_POST) exit();
		
		$out_summ = $_POST["OutSum"];
		$inv_id = $_POST["InvId"];
		$shp_account = $_POST["Shp_account"];
		
		$log = new LogBilling;
		$log->pay_id = $inv_id;
		$log->sum = $out_summ;
		$log->account = $shp_account;
		$log->status = 'fail';
		$log->system = 'Robokassa';
		$log->save(false);
		
		$this->redirect(Yii::app()->homeUrl.'account');
	}
	
	
	public function actionIkStatus()
	{
		if (!$_POST) exit();
		
		$model = SettingsPay::model()->find();
		$secret_key = $model->secret_key;
		$ik_shop_id = $model->ik_shop_id;
		$ik_payment_amount = $_POST['ik_payment_amount'];
		$ik_payment_id = $_POST['ik_payment_id'];
		$ik_paysystem_alias = $_POST['ik_paysystem_alias'];
		$ik_baggage_fields = $_POST['ik_baggage_fields'];
		$ik_payment_state = $_POST['ik_payment_state'];
		$ik_trans_id = $_POST['ik_trans_id'];
		$ik_currency_exch = $_POST['ik_currency_exch'];
		$ik_fees_payer = $_POST['ik_fees_payer'];
		$sign_hash = strtoupper(md5($ik_shop_id.':'.$ik_payment_amount.':'.$ik_payment_id.':'.$ik_paysystem_alias.':'.$ik_baggage_fields.':'.$ik_payment_state.':'.$ik_trans_id.':'.$ik_currency_exch.':'.$ik_fees_payer.':'.$secret_key));
		
		if($_POST['ik_sign_hash'] === $sign_hash)
		{
			$log = new LogBilling;
			$log->pay_id = $ik_trans_id;
			$log->sum = $ik_payment_amount;
			$log->account = $ik_payment_id;
			$log->status = 'unpaid';
			$log->system = 'Interkassa';
			$log->save(false);
			
			$status = LogBilling::model()->findByPK($ik_trans_id);
			
			if ($status->status == 'unpaid')
			{
				$criteria = new CDbCriteria;
				$criteria->condition = 'name = "'.$ik_payment_id.'"';
				$money = AccountData::model()->find($criteria);
				$money[Yii::app()->params->money] = $money[Yii::app()->params->money] + $ik_payment_amount;
				$money->save();
				
				$log = LogBilling::model()->findByPK($ik_trans_id);
				$log->status = 'complete';
				$log->save(false);
				
				echo 'status - success';
			}
			else {
				echo 'fail';
				exit();
			}
		}
		else {
			echo 'Hash check failed.';
		}

	}
	
	public function actionIkSuccess()
	{
		$this->redirect(Yii::app()->homeUrl.'account');
	}
	
	public function actionIkFail()
	{
		$this->redirect(Yii::app()->homeUrl.'account');
	}
}