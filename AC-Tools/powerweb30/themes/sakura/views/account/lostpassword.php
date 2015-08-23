<div class="note">
	<div class="note_body">
		<div class="note_title">Восстановление пароля</div>
		<?php if(Yii::app()->user->hasFlash('message')) echo Yii::app()->user->getFlash('message'); ?>
		<?php echo CHtml::beginForm(); ?>
		<div class="form"><?php echo CHtml::errorSummary($model)?></div>
		<table class="table_info">
			<tr>
				<td width="200px"><label for="AccountData_name">Логин</label></td>
				<td><?php echo CHtml::activeTextField($model,'name') ?></td>
			</tr>
			<tr>
				<td><label for="AccountData_email">Email адрес</label></td>
				<td><?php echo CHtml::activeTextField($model,'email') ?></td>
			</tr>
			<tr>
				<td><?php $this->widget('CCaptcha', array('showRefreshButton' => false, 'clickableImage' => true)); ?></td>
				<td><?php echo CHtml::activeTextField($model,'captcha') ?></td>
			</tr>
			<tr>
				<td class="center" colspan="2"><input type="submit" class="button" name="yt0" value="Восстановить"></td>
		</table>
		<?php echo CHtml::endForm(); ?>
	</div>
</div>