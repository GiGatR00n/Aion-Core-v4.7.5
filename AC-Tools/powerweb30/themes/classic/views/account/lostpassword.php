






<div class="note">
	<div class="note_title">Password recovery</div>
	
	<div class="note_body">
		<?php if(Yii::app()->user->hasFlash('message')) echo Yii::app()->user->getFlash('message'); ?>
		<?php echo CHtml::beginForm(); ?>
		<div class="form"><?php echo CHtml::errorSummary($model)?></div>
		
		<table class="table_info">
			<tr>
				<td width="200px"><label for="AccountData_name">Username</label></td>
				<td><?php echo CHtml::activeTextField($model,'name') ?></td>
			</tr>
			<tr>
				<td><label for="AccountData_email">Email address</label></td>
				<td><?php echo CHtml::activeTextField($model,'email') ?></td>
			</tr>
			<tr>
				<td><?php $this->widget('CCaptcha', array('showRefreshButton' => false, 'clickableImage' => true)); ?></td>
				<td><?php echo CHtml::activeTextField($model,'captcha') ?></td>
			</tr>
			<tr>
				<td class="center" colspan="2"><input type="submit" class="button" name="yt0" value="Recover details"></td>
		</table>
		<?php echo CHtml::endForm(); ?>
	</div>
</div>