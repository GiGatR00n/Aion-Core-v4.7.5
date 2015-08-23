






<div class="note mb15">
	<div class="note_title">
		<a href="<?php echo Yii::app()->homeUrl; ?>admin">Admin area</a> &rarr; Search
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
				<td><input type="submit" value="Найти" name="yt0" class="button" /></td>
			</tr>
		</table>
		<?php $this->endWidget(); ?>
	</div>
</div>

<div class="note">
	<div class="note_title">
		<a href="<?php echo Yii::app()->homeUrl; ?>admin">Admin area</a> &rarr; Item list
	</div>
	<div class="note_body">
		<?php
			$this->widget('zii.widgets.grid.CGridView', array(
				'id'=>'webshop-grid',
				'summaryText' => false,
				'dataProvider'=>$model->search(),
			    'columns' => array(
					array(
						'type' => 'raw',
						'value' => 'Adb::ico($data->item_id)',
					),
					array(
						'name' => 'name',
						'htmlOptions'=>array('class'=>'tleft'),
						'value' => '$data->name',
					),
					array(
					   'name' => 'level',
					   'value' => '$data->level',
					),
					array(
					   'name' => 'category_id',
					   'value' => '$data->category->name',
					),
					array(
					   'name' => 'amount',
					   'value' => '$data->amount',
					),
					array(
					   'name' => 'price',
					   'value' => '$data->price',
					),
					array(
						'class' => 'CButtonColumn',
						'header' => 'Edit/delete',
						'template'=>'{edit} {delete}',
						'buttons'=>array (
							'edit' => array (
								'label' => 'Edit',
								'imageUrl' => Yii::app()->homeUrl.'images/edit.png',
								'url' => 'Yii::app()->homeUrl."admin/webshop/edit/".$data->item_id',
							),
							'delete' => array (
								'label' => 'Delete',
								'imageUrl' => Yii::app()->homeUrl.'images/delete.png',
								'url' => 'Yii::app()->homeUrl."admin/webshop/delete/".$data->item_id',
							),
						),
					),
			    ),
			));
		?>
		<div class="clear"></div>
	</div>
</div>