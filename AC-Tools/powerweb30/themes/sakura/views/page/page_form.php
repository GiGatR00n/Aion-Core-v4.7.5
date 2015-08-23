<link rel="stylesheet" href="<?php echo Yii::app()->homeUrl; ?>js/elrte/css/elrte.min.css" type="text/css" charset="utf-8">
<script src="<?php echo Yii::app()->homeUrl; ?>js/elrte/js/elrte.min.js" type="text/javascript" charset="utf-8"></script>
<script src="<?php echo Yii::app()->homeUrl; ?>js/elrte/js/i18n/elrte.ru.js" type="text/javascript" charset="utf-8"></script>

<script type="text/javascript" charset="utf-8">
	jQuery().ready(function() {
		var opts = {
			cssClass : 'el-rte',
			lang     : 'ru',
			height   : 450,
			toolbar  : 'complete',
			cssfiles : ['<?php echo Yii::app()->homeUrl; ?>js/elrte/css/elrte-inner.css']
		}
		jQuery('#Pages_text').elrte(opts);
	})
</script>

<div class="note">
	<div class="note_body">
		<div class="note_title">Добавление страницы</div>
		<?php $form=$this->beginWidget('CActiveForm', array(
			'id'=>'news-addnews-form',
			'enableAjaxValidation'=>false,
		)); ?>
		<div class="form"><?php echo $form->errorSummary($model); ?></div>
		<table class="table_info">
			<tr>
				<td width="150px"><?php echo $form->labelEx($model,'title'); ?></td>
				<td><?php echo $form->textField($model,'title',array('size'=>64,'maxlength'=>128)); ?></td>
			</tr>
			<tr>
				<td width="150px"><?php echo $form->labelEx($model,'name'); ?></td>
				<td><?php echo $form->textField($model,'name',array('maxlength'=>128)); ?></td>
			</tr>
			<tr>
				<td><?php echo $form->labelEx($model,'text'); ?></td>
			</tr>
			<tr>
				<td colspan="2"><?php echo CHtml::activeTextArea($model,'text'); ?></td>
			</tr>
			<tr>
				<td><?php echo $form->labelEx($model,'description'); ?></td>
				<td><?php echo $form->textField($model,'description',array('size'=>64,'maxlength'=>255)); ?></td>
			</tr>
			<tr>
				<td><?php echo $form->labelEx($model,'keywords'); ?></td>
				<td><?php echo $form->textField($model,'keywords',array('size'=>64,'maxlength'=>255)); ?></td>
			</tr>
			<tr>
				<td colspan="2" align="center">
					<?php echo CHtml::submitButton($model->isNewRecord ? 'Добавить страницу' : 'Сохранить изменения', array('class'=>'button')); ?>
					<?php if(!$model->isNewRecord):?><input type="button" ONCLICK="location.href='<?php echo Yii::app()->homeUrl; ?>page/delete/<?php echo $model->id; ?>'" class="button" value="Удалить страницу" /><?php endif; ?>
				</td>
			</tr>
		</table>
		<?php $this->endWidget(); ?>
	</div>
</div>