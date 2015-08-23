<div class="note">
	<div class="note_body">
		<div class="note_title">Регистрация аккаунта</div>
		<?php if(Yii::app()->user->hasFlash('message')) echo Yii::app()->user->getFlash('message'); ?>
		<?php echo CHtml::beginForm(); ?>
		<div class="form"><?php echo CHtml::errorSummary($form)?></div>
		<table class="table_info">
			<tr>
				<td width="200px"><?php echo CHtml::activeLabel($form,'name')?></td>
				<td><?php echo CHtml::activeTextField($form,'name'); ?></td>
			</tr>
			<tr>
				<td><?php echo CHtml::activeLabel($form,'password'); ?></td>
				<td><?php echo CHtml::activePasswordField($form,'password'); ?></td>
			</tr>
			<tr>
				<td><?php echo CHtml::activeLabel($form,'password_repeat'); ?></td>
				<td><?php echo CHtml::activePasswordField($form,'password_repeat'); ?></td>
			</tr>
			<tr>
				<td><?php echo CHtml::activeLabel($form,'email'); ?></td>
				<td><?php echo CHtml::activeTextField($form,'email') ?></td>
			</tr>
			<tr>
				<td><?php $this->widget('CCaptcha', array('showRefreshButton' => false, 'clickableImage' => true)); ?></td>
				<td><?php echo CHtml::activeTextField($form,'captcha') ?></td>
			</tr>
			<tr class="center">
				<td colspan="2"><input type="submit" value="Зарегистрироваться" name="yt0" class="button"></td>
			</tr>
		</table>
		<?php echo CHtml::endForm(); ?>
	</div>
</div>