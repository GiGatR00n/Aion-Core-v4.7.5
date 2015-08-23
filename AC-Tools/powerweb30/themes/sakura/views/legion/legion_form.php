<div class="note">
	<div class="note_body">
		<div class="note_title"><a href="<?php echo Yii::app()->homeUrl; ?>admin">Админцентр</a> &rarr; Редактирование легиона</div>
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
				<td><?php echo $form->labelEx($model,'rank'); ?></td>
				<td><?php echo $form->textField($model,'rank'); ?></td>
			</tr>
			<tr>
				<td><?php echo $form->labelEx($model,'level'); ?></td>
				<td><?php echo $form->textField($model,'level'); ?></td>
			</tr>
			<tr>
				<td><?php echo $form->labelEx($model,'contribution_points'); ?></td>
				<td><?php echo $form->textField($model,'contribution_points'); ?></td>
			</tr>
			<tr>
				<td class="center" colspan="2"><input type="submit" class="button" name="yt0" value="Сохранить"></td>
			</tr>
		</table>
		<?php $this->endWidget(); ?>
	</div>
</div>