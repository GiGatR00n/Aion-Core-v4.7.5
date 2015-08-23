






<div class="note mb15">
	<div class="note_title">
		<a href="<?php echo Yii::app()->homeUrl; ?>admin">Admin area</a> &rarr; Legions search
	</div>
	<div class="note_body">
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
				<td><input type="submit" value="Search" name="yt0" class="button" /></td>
			</tr>
		</table>
		<?php $this->endWidget(); ?>
	</div>
</div>

<div class="note">
	<div class="note_title">
		<a href="<?php echo Yii::app()->homeUrl; ?>admin">Admin area</a> &rarr; Search results (legions)
	</div>
	<div class="note_body">
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