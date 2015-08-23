






<div class="note mb15">
	<div class="note_title"><a href="<?php echo Yii::app()->homeUrl; ?>admin">Admin area</a> &rarr; Manage privileges</div>
	<div class="note_body">
		<?php if (Yii::app()->user->hasFlash('message')) echo Yii::app()->user->getFlash('message'); ?>
		<?php echo CHtml::beginForm(); ?>
		<div class="form"><?php echo CHtml::errorSummary($form)?></div>
		<table class="items">
			<tr>
				<td width="200px"><?php echo CHtml::activeLabel($form,'title'); ?></td>
				<td><?php echo CHtml::activeTextField($form,'title',array('maxlength'=>64)); ?></td>
			</tr>
			<tr>
				<td><?php echo CHtml::activeLabel($form,'type'); ?></td>
				<td><?php echo CHtml::activeTextField($form,'type'); ?></td>
			</tr>
			<tr>
				<td><?php echo CHtml::activeLabel($form,'duration'); ?></td>
				<td><?php echo CHtml::activeTextField($form,'duration'); ?></td>
			</tr>
			<tr>
				<td><?php echo CHtml::activeLabel($form,'price'); ?></td>
				<td><?php echo CHtml::activeTextField($form,'price'); ?></td>
			</tr>
			<tr class="center">
				<td colspan="2">
					<?php echo CHtml::submitButton($form->isNewRecord ? 'Add privileges' : 'Save changes', array('class'=>'button')); ?>
					<?php if(!$form->isNewRecord):?><input type="button" ONCLICK="location.href='<?php echo Yii::app()->homeUrl.'admin/webshop/mdelete/'.$form->id; ?>'" class="button" value="Remove privileges" /><?php endif; ?>
				</td>
			</tr>
		</table>
		<?php echo CHtml::endForm(); ?>
	</div>
</div>

<div class="note">
	<div class="note_title"><a href="<?php echo Yii::app()->homeUrl; ?>admin">Admin area</a> &rarr; Privileges list</div>
	<div class="note_body">
		<table class="items">
			<tr>
				<th>Title</th>
				<th>Type</th>
				<th>Duration</th>
				<th>Price</th>
				<th colspan="2">Edit/delete</th>
			</tr>
		<?php foreach($model as $data): ?>
			<tr align="center">
				<td><?php echo $data->title; ?></td>
				<td><?php echo Info::membership($data->type); ?></td>
				<td><?php echo $data->duration; ?></td>
				<td><?php echo $data->price; ?></td>

				<td width="36px"><a href="<?php echo Yii::app()->homeUrl.'admin/webshop/membership/'.$data->id; ?>" /><img src="<?php echo Yii::app()->homeUrl; ?>images/edit.png" title="Edit" /></a></td>
				<td width="36px"><a href="<?php echo Yii::app()->homeUrl.'admin/webshop/mdelete/'.$data->id; ?>" /><img src="<?php echo Yii::app()->homeUrl; ?>images/delete.png" title="Delete" /></a></td>
			</tr>
		<?php endforeach; ?>
		</table>
	</div>
</div>
