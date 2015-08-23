






<div class="note mb15">
	<div class="note_title">Change details</div>
	
	<div class="note_body">
		<?php if (Yii::app()->user->hasFlash('message')) echo Yii::app()->user->getFlash('message'); ?>
		<?php echo CHtml::beginForm(); ?>
		<div class="form"><?php echo CHtml::errorSummary($form)?></div>
		
		<table class="table_info">
			<tr>
				<td width="300px"><label for="AccountData_password">New password</label></td>
				<td><?php echo CHtml::activePasswordField($form,'new_password'); ?></td>
			</tr>
			<tr>
				<td><label for="AccountData_password_repeat">Confirm new password</label></td>
				<td><?php echo CHtml::activePasswordField($form,'password_repeat'); ?></td>
			</tr>
			<tr>
				<td><label for="AccountData_email">Email address</label></td>
				<td><?php echo CHtml::activeTextField($form,'email') ?></td>
			</tr>
			<tr>
				<td><label for="AccountData_current_password">Current password</label></td>
				<td><?php echo CHtml::activePasswordField($form,'current_password'); ?></td>
			</tr>
			<tr>
				<td class="center" colspan="2"><input type="submit" class="button" name="yt0" value="Save changes"></td>
			</tr>
		</table>
		<?php echo CHtml::endForm(); ?>
	</div>
</div>


<div class="note">
	<div class="note_title">Character settings</div>
	
	<div class="note_body">
		<?php if (Yii::app()->user->hasFlash('message2')) echo Yii::app()->user->getFlash('message2'); ?>
		<?php $form=$this->beginWidget('CActiveForm', array(
			'id'=>'players-form',
			'enableAjaxValidation'=>true,
		)); ?>
		<div class="form"><?php echo $form->errorSummary($form2); ?></div>
		<table class="items">
			<tr>
				<th>Name</th>
				<th>Level</th>
				<th>Race</th>
				<th>Class</th>
				<th width="80px">Inventory</th>
				<th width="80px">Location</th>
			</tr>
		<?php foreach ($form2 as $data): ?>
			<tr class="center">
				<td><a href="<?php echo Yii::app()->homeUrl.'player/'.$data->name; ?>"><?php echo $data->name; ?></a></td>
				<td><?php echo Info::lvl($data->exp); ?></td>
				<td><?php echo Info::race($data->race) ?></td>
				<td><?php echo Info::player_class($data->player_class) ?></td>
				
				<td>
					<?php if ($data->show_inventory == 1): ?>
						<input type="checkbox" id="Players_show_inventory" name="Players[show_inventory][<?php echo $data->id; ?>]" checked="checked">
					<?php else: ?>	
						<input type="checkbox" id="Players_show_inventory" name="Players[show_inventory][<?php echo $data->id; ?>]">
					<?php endif; ?>
				</td>

				
				<td>
					<?php if ($data->show_location == 1): ?>
						<input type="checkbox" id="Players_show_location" name="Players[show_location][<?php echo $data->id; ?>]" checked="checked">
					<?php else: ?>	
						<input type="checkbox" id="Players_show_location" name="Players[show_location][<?php echo $data->id; ?>]">
					<?php endif; ?>
				</td>
				
			</tr>
		<?php endforeach ?>
			<tr class="center">
				<input type="hidden" name="Players[check]">
				<td colspan="6"><input type="submit" value="Save changes" name="yt0" class="button"></td>
			</tr>
		</table>
		<?php $this->endWidget(); ?>
	</div>
</div>