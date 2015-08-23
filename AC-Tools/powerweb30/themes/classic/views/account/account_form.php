






<script>
	$(function() {
		$( "#AccountData_expire" ).datepicker({
			numberOfMonths: 2,
			firstDay: 1,
			dateFormat: 'yy-mm-dd',
			showButtonPanel: false
		});
	});
</script>

<div class="note">
	<div class="note_title"><a href="<?php echo Yii::app()->homeUrl; ?>admin">Admin area</a> &rarr; Edit account</div>
	
	<div class="note_body">
		<?php if(Yii::app()->user->hasFlash('message')) echo Yii::app()->user->getFlash('message'); ?>
		<?php echo CHtml::beginForm(); ?>
		<div class="form"><?php echo CHtml::errorSummary($model)?></div>
		
		<table class="table_info">
			<tr>
				<td width="300px"><label for="AccountData_name">Name</label></td>
				<td><?php echo CHtml::activeTextField($model,'name'); ?></td>
			</tr>
			<tr>
				<td><label for="AccountData_email">Email</label></td>
				<td><?php echo CHtml::activeTextField($model,'email'); ?></td>
			</tr>
			<tr>
				<td><label for="AccountData_access_level">Access level</label></td>
				<td><?php echo CHtml::activeTextField($model,'access_level'); ?></td>
			</tr>
			<tr>
				<td><label for="AccountData_membership">Privileges</label></td>
				<td><?php echo CHtml::activeTextField($model,'membership'); ?></td>
			</tr>
			<tr>
				<td><label for="AccountData_expire">Privileges expiry date</label></td>
				<td><?php echo CHtml::activeTextField($model,'expire'); ?></td>
			</tr>
			<tr>
				<td><label for="AccountData_toll">Balance</label></td>
				<td><?php echo CHtml::activeTextField($model,'toll') ?></td>
			</tr>
			<tr>
				<td><label for="AccountData_activated">Active account</label></td>
				<td>
					<?php if ($model->activated == 1): ?>
						<input type="checkbox" id="AccountData_activated" name="AccountData[activated]" checked="checked">
					<?php else: ?>	
						<input type="checkbox" id="AccountData_activated" name="AccountData[activated]">
					<?php endif; ?>
				</td>
			</tr>
			<tr>
				<td><label for="AccountData_new_password">New password</label></td>
				<td><?php echo CHtml::activeTextField($model,'new_password') ?></td>
			</tr>
			<tr>
				<td class="center" colspan="2"><input type="submit" class="button" name="yt0" value="Save changes"></td>
		</table>
		<?php echo CHtml::endForm(); ?>
	</div>
</div>