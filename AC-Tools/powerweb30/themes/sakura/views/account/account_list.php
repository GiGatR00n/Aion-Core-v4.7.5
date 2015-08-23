<div class="note mb15">
	<div class="note_body">
		<div class="note_title"><a href="<?php echo Yii::app()->homeUrl; ?>admin">Админцентр</a> &rarr; Поиск аккаунтов</div>
		<?php $form=$this->beginWidget('CActiveForm', array(
			'action'=>Yii::app()->createUrl($this->route),
			'method'=>'get',
		)); ?>
		<table class="table_info">
			<tr>
				<td width="200px"><?php echo $form->label($model,'id'); ?></td>
				<td><?php echo $form->textField($model,'id'); ?></td>
			</tr>
			<tr>
				<td><?php echo $form->label($model,'name'); ?></td>
				<td><?php echo $form->textField($model,'name'); ?></td>
			</tr>
			<tr>
				<td><?php echo $form->label($model,'email'); ?></td>
				<td><?php echo $form->textField($model,'email'); ?></td>
			</tr>
			<tr>
				<td><?php echo $form->label($model,'last_ip'); ?></td>
				<td><?php echo $form->textField($model,'last_ip'); ?></td>
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
		<div class="note_title"><a href="<?php echo Yii::app()->homeUrl; ?>admin">Админцентр</a> &rarr; Список аккаунтов</div>
		<?php
			$this->widget('zii.widgets.grid.CGridView', array(
				'id'=>'accountData-grid',
				'summaryText' => false,
				'dataProvider'=>$model->search(),
			    'columns' => array(
			        array(
			            'name' => 'id',
			            'value' => '$data->id',
			        ),
					array(
			           'name' => 'name',
			           'type' => 'raw',
					   'value' => 'CHtml::link($data->name, array("admin/account", "id" => $data->name))',
			        ),
					array(
			           'name' => 'email',
			           'value' => '$data->email',
			        ),
			        array(
			           'name' => 'access_level',
			           'type' => 'raw',
			           'value' => 'Info::access_level_ico($data->access_level)',
			        ),
					array(
			           'name' => 'membership',
			           'type' => 'raw',
			           'value' => 'Info::membership_ico($data->membership)',
			        ),
					array(
			           'name' => 'last_ip',
						'value' => '$data->last_ip',
			        ),
			    ),
			));
		?>
	</div>
</div>