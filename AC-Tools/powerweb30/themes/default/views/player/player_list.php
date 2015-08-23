






<div class="note mb15">
	<div class="note_title">
		<a href="<?php echo Yii::app()->homeUrl; ?>admin">Admin area</a> &rarr; Character search
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
				<td><?php echo $form->label($model,'account_name'); ?></td>
				<td><?php echo $form->textField($model,'account_name'); ?></td>
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
		<a href="<?php echo Yii::app()->homeUrl; ?>admin">Admin area</a> &rarr; Search results (characters)
	</div>
	<div class="note_body">
		<?php
			$this->widget('zii.widgets.grid.CGridView', array(
				'id'=>'players-grid',
				'summaryText' => false,
				'dataProvider'=>$model->search(),
			    'columns' => array(
			        array(
			            'name' => 'name',
			            'type' => 'raw',
			            'value' => 'CHtml::link($data->name, array("admin/player", "id" => $data->name))',
			        ),
					array(
			           'name' => 'account_name',
			           'type' => 'raw',
					    'value' => 'CHtml::link($data->account_name, array("admin/account", "id" => $data->account_name))',
			        ),
					array(
			           'name' => 'exp',
			           'type' => 'text',
			           'value' => 'Info::lvl($data->exp)',
			        ),
			        array(
			           'name' => 'race',
			           'type' => 'raw',
			           'value' => 'Info::race($data->race)',
			        ),
					array(
			           'name' => 'player_class',
			           'type' => 'raw',
			           'value' => 'Info::player_class($data->player_class)',
			        ),
			    ),
			));
		?>
		<div class="clear"></div>
	</div>
</div>