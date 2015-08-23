






<div class="note">
	<div class="note_title">
		<a href="<?php echo Yii::app()->homeUrl; ?>admin">Admin area</a> &rarr; Send mail
	</div>
	<div class="note_body">
		<?php if(Yii::app()->user->hasFlash('message')) echo Yii::app()->user->getFlash('message'); ?>
		<?php $form=$this->beginWidget('CActiveForm', array(
				'id'=>'user-form',
				'enableClientValidation'=>true,
				'clientOptions'=>array(
					'validateOnSubmit'=>true,
				),
		)); ?>
		
		<table class="table_info">
			<tr>
				<td width="300px"><?php echo $form->labelEx($model,'mail_title'); ?></td>
				<td><?php echo $form->textField($model, 'mail_title', array('size'=>58)); ?></td>
			</tr>
			<tr>
				<td><?php echo $form->labelEx($model,'player_name'); ?></td>
				<td><?php echo $form->textField($model, 'player_name'); ?></td>
			</tr>
			<tr>
				<td><?php echo $form->labelEx($model,'item_id'); ?></td>
				<td><?php echo $form->textField($model, 'item_id'); ?></td>
			</tr>
			<tr>
				<td><?php echo $form->labelEx($model,'item_count'); ?></td>
				<td><?php echo $form->textField($model, 'item_count'); ?></td>
			</tr>
			<tr>
				<td><?php echo $form->labelEx($model,'mail_message'); ?></td>
				<td><?php echo $form->textArea($model, 'mail_message', array('rows'=>10,'cols'=>45)); ?></td>
			</tr>
			<tr>
				<td class="center" colspan="2"><input type="submit" class="button" name="yt0" value="Send mail"></td>
			</tr>
		</table>
		<?php $this->endWidget(); ?>
	</div>
</div>