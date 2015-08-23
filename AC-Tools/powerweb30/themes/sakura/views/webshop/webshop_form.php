<div class="note">
	<div class="note_body">
		<div class="note_title"><a href="<?php echo Yii::app()->homeUrl; ?>admin">Админцентр</a> &rarr; Добавление вещей</div>
		<?php if(Yii::app()->user->hasFlash('message')) echo Yii::app()->user->getFlash('message'); ?>
		<?php $form=$this->beginWidget('CActiveForm', array(
			'id'=>'user-form',
			'enableAjaxValidation'=>true,
			'enableClientValidation'=>false,
			'clientOptions'=>array(
				'validateOnSubmit'=>true,
			),
		)); ?>
		<div class="form"><?php echo $form->errorSummary($model); ?></div>
		<table class="table_info">
			<tr>
				<td width="300px"><?php echo $form->labelEx($model,'item_id'); ?></td>
				<td><?php echo $form->textField($model,'item_id'); ?></td>
			</tr>
			<tr>
				<td><?php echo $form->labelEx($model,'category_id'); ?></td>
				<td><?php echo $form->dropDownList($model,'category_id', CHtml::listData(WebshopCategory::model()->findAll(), 'category_id', 'name')); ?></td>
			</tr>
			<tr>
				<td><?php echo $form->labelEx($model,'amount'); ?></td>
				<td><?php echo $form->textField($model,'amount'); ?></td>
			</tr>
			<tr>
				<td><?php echo $form->labelEx($model,'price'); ?></td>
				<td><?php echo $form->textField($model,'price'); ?></td>
			</tr>
			<tr>
				<td><?php echo $form->labelEx($model,'edit'); ?></td>
				<td><?php echo $form->checkBox($model,'edit'); ?></td>
			</tr>

			<tr>
				<td class="center" colspan="2"><input type="submit" class="button" name="yt0" value="Сохранить"></td>
			</tr>
		</table>
		<?php $this->endWidget(); ?>
	</div>
</div>