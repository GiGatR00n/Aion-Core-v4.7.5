<!--<form action='http://test.robokassa.ru/Index.aspx' method=POST>-->
<form action='https://merchant.roboxchange.com/Index.aspx' method=POST>
	<input type="hidden" name="MrchLogin" value="<?php echo $model->mrh_login; ?>">
	<input type="hidden" name="OutSum" >
	<input type="hidden" name="InvId" value=0>
	<input type="hidden" name="Shp_account" value="<?php echo Yii::app()->user->name; ?>">
	<input type="hidden" name="Desc" value="<?php echo $model->inv_desc; ?>">
	<input type="hidden" name="IncCurrLabel" value="WMRM">
	<input type="hidden" name="Culture" value="ru">
	<input type="hidden" name="SignatureValue" value="<?php echo $crc; ?>">
	<input type="submit" class="button" value="Top up balance with Robokassa!" />
</form>