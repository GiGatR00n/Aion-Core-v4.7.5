<div class="note">
	<div class="note_body">
		<div class="note_title"><a href="<?php echo Yii::app()->homeUrl; ?>admin">Админцентр</a> &rarr; Редактирование персонажа</div>
		<?php if(Yii::app()->user->hasFlash('message')) echo Yii::app()->user->getFlash('message'); ?>
		<?php $form=$this->beginWidget('CActiveForm', array(
				'id'=>'players-form',
				'enableClientValidation'=>true,
				'clientOptions'=>array(
					'validateOnSubmit'=>true,
				),
		)); ?>
		<div class="form"><?php echo CHtml::errorSummary($model)?></div>
		<table class="table_info">
			<tr>
				<td width="300px"><?php echo $form->labelEx($model,'name'); ?></td>
				<td><?php echo $form->textField($model,'name'); ?></td>
			</tr>
			<tr>
				<td><label for="Players_exp">Опыт</label></td>
				<td><?php echo $form->textField($model,'exp'); ?></td>
			</tr>
			<tr>
				<td><?php echo $form->labelEx($model,'npc_expands'); ?></td>
				<td><?php echo $form->textField($model,'npc_expands'); ?></td>
			</tr>
			<tr>
				<td><?php echo $form->labelEx($model,'advanced_stigma_slot_size'); ?></td>
				<td><?php echo $form->textField($model,'advanced_stigma_slot_size'); ?></td>
			</tr>
			<tr>
				<td><?php echo $form->labelEx($model,'warehouse_size'); ?></td>
				<td><?php echo $form->textField($model,'warehouse_size'); ?></td>
			</tr>
			<tr>
				<td><?php echo $form->labelEx($model,'bind_point'); ?></td>
				<td><?php echo $form->textField($model,'bind_point'); ?></td>
			</tr>
			<tr>
				<td><?php echo $form->labelEx($model,'title_id'); ?></td>
				<td><?php echo $form->textField($model,'title_id'); ?></td>
			</tr>
			<tr>
				<td><?php echo $form->labelEx($model,'show_inventory'); ?></td>
				<td><?php echo $form->checkBox($model,'show_inventory'); ?></td>
			</tr>
			<tr>
				<td><?php echo $form->labelEx($model,'show_location'); ?></td>
				<td><?php echo $form->checkBox($model,'show_location'); ?></td>
			</tr>
			<tr>
				<td class="center" colspan="2"><input type="submit" class="button" name="yt0" value="Сохранить"></td>
			</tr>
		</table>
		<?php $this->endWidget(); ?>
	</div>
</div>