<div class="note">
	<div class="note_body">
		<div class="note_title"><a href="<?php echo Yii::app()->homeUrl; ?>admin">Админцентр</a> &rarr; Настройка рейтингов</div>
		<?php if(Yii::app()->user->hasFlash('message')) echo Yii::app()->user->getFlash('message'); ?>
		
		<?php $form = $this->beginWidget('CActiveForm', array(
			'id'=>'voteConfig-form',
			'enableAjaxValidation'=>true,
			'enableClientValidation'=>true,
			//'focus'=>array($model,'firstName'),
		)); ?>
		<div class="form"><?php echo $form->errorSummary($model); ?></div>
		<table class="table_info center">
			<tr>
				<th>Название</th>
				<th>Ссылка на статистику топа</th>
				<th>Цена</th>
				<th>Цена СМС</th>
			</tr>
			<tr>
				<td class="left">aion-top.info</td>
				<td><?php echo CHtml::activeTextField($model,'aiontop_link', array('size'=>50,'maxlength'=>128)); ?></td>
				<td><?php echo CHtml::activeTextField($model,'aiontop_reg', array('size'=>3,'maxlength'=>4)); ?></td>
				<td></td>
			</tr>
			<tr>
				<td class="left">aion.l2top.ru</td>
				<td><?php echo CHtml::activeTextField($model,'l2top_link', array('size'=>50,'maxlength'=>128)); ?></td>
				<td><?php echo CHtml::activeTextField($model,'l2top_reg', array('size'=>3,'maxlength'=>4)); ?></td>
				<td></td>
			</tr>
			<tr>
				<td class="left">aion.mmotop.ru</td>
				<td><?php echo CHtml::activeTextField($model,'mmotop_link', array('size'=>50,'maxlength'=>128)); ?></td>
				<td><?php echo CHtml::activeTextField($model,'mmotop_reg', array('size'=>3,'maxlength'=>4)); ?></td>
				<td><?php echo CHtml::activeTextField($model,'mmotop_sms', array('size'=>3,'maxlength'=>4)); ?></td>
			</tr>
			<tr>
				<td class="left">gtop100.com/aion</td>
				<td><?php echo CHtml::activeTextField($model,'gtop_link', array('size'=>50,'maxlength'=>128)); ?></td>
				<td><?php echo CHtml::activeTextField($model,'gtop_reg', array('size'=>3,'maxlength'=>4)); ?></td>
			</tr>
			<tr>
				<td class="left">gamesites200.com/aion</td>
				<td><?php echo CHtml::activeTextField($model,'gamesites_link', array('size'=>50,'maxlength'=>128)); ?></td>
				<td><?php echo CHtml::activeTextField($model,'gamesites_reg', array('size'=>3,'maxlength'=>4)); ?></td>
			</tr>
			<tr>
				<td class="left">xtremetop100.com</td>
				<td><?php echo CHtml::activeTextField($model,'xtremetop_link', array('size'=>50,'maxlength'=>128)); ?></td>
				<td><?php echo CHtml::activeTextField($model,'xtremetop_reg', array('size'=>3,'maxlength'=>4)); ?></td>
			</tr>
			<tr class="center">
				<td colspan="4"><input class="button" type="submit" value="Сохранить" name="yt0"></td>
			</tr>
		</table>
		<?php $this->endWidget(); ?>
	</div>
</div>