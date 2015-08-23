






<div class="note">
	<div class="note_title">
		<a href="<?php echo Yii::app()->homeUrl; ?>admin">Admin area</a> &rarr; Payment system configuration
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
				<td class="bold">Robokassa settings:</td>
			</tr>
			<tr>
				<td width="300px"><?php echo $form->labelEx($model,'mrh_login'); ?></td>
				<td><?php echo $form->textField($model, 'mrh_login'); ?></td>
			</tr>
			<tr>
				<td><?php echo $form->labelEx($model,'mrh_pass1'); ?></td>
				<td><?php echo $form->textField($model, 'mrh_pass1'); ?></td>
			</tr>
			<tr>
				<td><?php echo $form->labelEx($model,'mrh_pass2'); ?></td>
				<td><?php echo $form->textField($model, 'mrh_pass2'); ?></td>
			</tr>
			<tr>
				<td><?php echo $form->labelEx($model,'inv_desc'); ?></td>
				<td><?php echo $form->textField($model, 'inv_desc'); ?></td>
			</tr>
			
			<tr>
				<td class="bold">Interkassa settings:</td>
			</tr>
			<tr>
				<td><?php echo $form->labelEx($model,'ik_shop_id'); ?></td>
				<td><?php echo $form->textField($model, 'ik_shop_id'); ?></td>
			</tr>
			<tr>
				<td><?php echo $form->labelEx($model,'secret_key'); ?></td>
				<td><?php echo $form->textField($model, 'secret_key'); ?></td>
			</tr>
			<tr>
				<td><?php echo $form->labelEx($model,'ik_payment_desc'); ?></td>
				<td><?php echo $form->textField($model, 'ik_payment_desc'); ?></td>
			</tr>
			<tr class="center">
				<td colspan="2"><input type="submit" value="Save changes" name="yt0" class="button"></td>
			</tr>
		</table>
		<?php $this->endWidget(); ?>
	</div>
</div>