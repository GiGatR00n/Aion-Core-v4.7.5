






<div class="note">
	<div class="note_title">
		<a href="<?php echo Yii::app()->homeUrl; ?>admin">Admin area</a> &rarr; Checking expired privileges...
	</div>
	<div class="note_body">
		<?php if(Yii::app()->user->hasFlash('message')) echo Yii::app()->user->getFlash('message'); ?>
		<table class="items">
			<tr>
				<th>Account</th>
				<th>Type</th>
				<th>Date</th>
			</tr>
		<?php if ($model) foreach($model as $data): ?>
			<tr class="center">
				<td><a href="<?php echo Yii::app()->homeUrl.'player/'.$data->name; ?>"><?php echo $data->name ;?></a></td>
				<td><?php echo $data->membership ;?></td>
				<td><?php echo $data->expire ;?></td>
			</tr>
		<?php endforeach; ?>
			<tr>
				<?php echo CHtml::beginForm(); ?>
				<td class="center" colspan="3"><input type="submit" class="button" name="Clear" value="Remove expired privileges"></td>
				<?php echo CHtml::endForm(); ?>
			</tr>
		</table>
	</div>
</div>