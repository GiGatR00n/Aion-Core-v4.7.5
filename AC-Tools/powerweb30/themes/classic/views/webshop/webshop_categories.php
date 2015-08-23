






<div class="note mb15">
	<div class="note_title"><a href="<?php echo Yii::app()->homeUrl; ?>admin">Admin area</a> &rarr; Manage categories</div>
	<div class="note_body">
		<?php if(Yii::app()->user->hasFlash('message')) echo Yii::app()->user->getFlash('message'); ?>
		<?php echo CHtml::beginForm(); ?>
		<div class="form"><?php echo CHtml::errorSummary($form)?></div>
		<table class="items">
			<tr>
				<td width="200px"><?php echo CHtml::activeLabel($form,'name'); ?></td>
				<td><?php echo CHtml::activeTextField($form,'name',array('maxlength'=>32)); ?></td>
			</tr>
			<tr>
				<td><?php echo CHtml::activeLabel($form,'alt_name'); ?></td>
				<td><?php echo CHtml::activeTextField($form,'alt_name',array('maxlength'=>32)); ?></td>
			</tr>
			<tr class="center">
				<td colspan="2">
					<?php echo CHtml::submitButton($form->isNewRecord ? 'Add category' : 'Save changes', array('class'=>'button')); ?>
					<?php if(!$form->isNewRecord):?><input type="button" ONCLICK="location.href='<?php echo Yii::app()->homeUrl.'admin/webshop/cdelete/'.$form->category_id; ?>'" class="button" value="Remove category" /><?php endif; ?>
				</td>
			</tr>
		</table>
		<?php echo CHtml::endForm(); ?>
	</div>
</div>

<div class="note">
	<div class="note_title"><a href="<?php echo Yii::app()->homeUrl; ?>admin">Admin area</a> &rarr; Category list</div>
	<div class="note_body">
		<table class="items">
			<tr>
				<th>ID</th>
				<th>Category</th>
				<th>Name</th>
				<th colspan="2">Edit/delete</th>
			</tr>
		<?php foreach($model as $data): ?>
			<tr align="center">
				<td><?php echo $data->category_id; ?></td>
				<td><a href="<?php echo Yii::app()->homeUrl.'webshop/'.$data->alt_name; ?>"><?php echo $data->name; ?></a></td>
				<td><?php echo $data->alt_name; ?></td>
				<td width="36px"><a href="<?php echo Yii::app()->homeUrl.'admin/webshop/categories/'.$data->category_id; ?>" /><img src="<?php echo Yii::app()->homeUrl; ?>images/edit.png" title="Edit" /></a></td>
				<td width="36px"><a href="<?php echo Yii::app()->homeUrl.'admin/webshop/cdelete/'.$data->category_id; ?>" /><img src="<?php echo Yii::app()->homeUrl; ?>images/delete.png" title="Delete" /></a></td>
			</tr>
		<?php endforeach; ?>
		</table>
	</div>
</div>
