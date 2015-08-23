<div class="note mb15">
	<div class="note_body">
		<div class="note_title"><a href="<?php echo Yii::app()->homeUrl; ?>admin">Админцентр</a> &rarr; Поиск легиона</div>
		<?php $form=$this->beginWidget('CActiveForm', array(
			'action'=>Yii::app()->createUrl($this->route),
			'method'=>'get',
		)); ?>
		<table class="table_info">
			<tr>
				<td><?php echo $form->label($model,'name'); ?></td>
				<td><?php echo $form->textField($model,'name'); ?></td>
			</tr>
			<tr>
				<td></td>
				<td><input type="submit" value="Найти" name="yt0" class="button" /></td>
			</tr>
		</table>
		<?php $this->endWidget(); ?>
	</div>
</div>

<div class="note">
	<div class="note_body">
		<div class="note_title"><a href="<?php echo Yii::app()->homeUrl; ?>admin">Админцентр</a> &rarr; Список легионов</div>
		<?php
			$this->widget('zii.widgets.grid.CGridView', array(
				'id'=>'legions-grid',
				'summaryText' => false,
				'dataProvider'=>$model->search(),
			    'columns' => array(
			        array(
			            'name' => 'name',
			            'type' => 'raw',
						'value' => 'CHtml::link($data->name, array("admin/legion", "id" => $data->name))',
			        ),
					'level',
			        'contribution_points',
					'membersCount',
			    ),
			));
		?>
		<div class="clear"></div>
	</div>
</div>