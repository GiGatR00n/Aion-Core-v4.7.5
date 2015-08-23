






<div class="note mb15">
	<div class="note_title">Account information</div>
	<div class="note_body">
		<table class="items">
			<tr>
				<td width="300">Name</td>
				<td><?php echo $model->name; ?></td>
			</tr>
			<tr>
				<td>E-mail</td>
				<td><?php echo $model->email; ?></td>
			</tr>
			<tr>
				<td>Referral link</td>
				<td><?php echo $ref_link; ?></td>
			</tr>
			<tr>
				<td>Access level</td>
				<td><?php echo Info::access_level($model->access_level); ?></td>
			</tr>
			<tr>
				<td>Account privileges</td>
				<td><?php echo Info::membership($model->membership); ?></td>
			</tr>
			<tr>
				<td>Privileges expiry date</td>
				<td><?php echo $model->expire; ?></td>
			</tr>
			<tr>
				<td>Last IP used</td>
				<td><?php echo $model->last_ip; ?></td>
			</tr>
			<tr>
				<td>Balance</td>
				<td><b><?php echo $model->toll; ?> Points</b></td>
			</tr>
			<tr>
				<td colspan="2" align="center">
					<?php $this->widget('application.components.WidgetRobo'); ?>
					<?php $this->widget('application.components.WidgetIk'); ?>
				</td>
			</tr>
		</table>
	</div>
</div>

<div class="note">
	<div class="note_title">Transfer points</div>
	<div class="note_body">
		<?php if(Yii::app()->user->hasFlash('message')) echo Yii::app()->user->getFlash('message'); ?>
		<?php echo CHtml::beginForm(); ?>
			<div class="form"><?php echo CHtml::errorSummary($form)?></div>
			<table class="h32">
				<tr>
					<td>
						<?php echo CHtml::activeLabel($form,'recipient')?>
						<?php echo CHtml::activeTextField($form,'recipient'); ?>
					</td>
					<td>
						<?php echo CHtml::activeLabel($form,'sum')?>
						<?php echo CHtml::activeTextField($form,'sum'); ?>
					</td>
					<td>
						<input type="submit" class="button" name="yt0" value="Submit">
					</td>
				</tr>
			</table>
		<?php echo CHtml::endForm(); ?>
	</div>
</div>