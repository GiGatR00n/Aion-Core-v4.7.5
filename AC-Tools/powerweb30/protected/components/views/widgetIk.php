<form name="payment" action="http://www.interkassa.com/lib/payment.php" method="post" target="_top">
	<input type="hidden" name="ik_shop_id" value="<?php echo $model->ik_shop_id; ?>">
	<input name="ik_payment_amount" value="50" size="2" />
	<input type="hidden" name="ik_payment_id" value="<?php echo Yii::app()->user->name; ?>">
	<input type="hidden" name="ik_payment_desc" value="<?php echo $model->ik_payment_desc; ?>">
	<input type="submit" name="process" class="button" value="Top up balance with Interkassa!">
</form>