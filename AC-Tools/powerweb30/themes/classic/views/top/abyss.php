<div class="note">
	<div class="note_title">Top Abyss players</div>
	<div class="note_body">
		<?php
			$this->widget('zii.widgets.grid.CGridView', array(
				'summaryText' => false,
				'dataProvider'=>$model->topSearch(),
			    'columns' => array(
			        array(
						'name' => 'name',
						'type' => 'raw',
						'value' => 'CHtml::link($data->name, array("admin/player", "id" => $data->name))',
			        ),
					array(
						'name' => 'exp',
						'type' => 'raw',
						'value' => 'Info::lvl($data->exp)',
			        ),
					array(
						'name'=>'all_kill',
						'type'=>'raw',
						'value' => '$data->abyssRank->all_kill',
					),
					array(
						'name'=>'ap',
						'type'=>'raw',
						'value' => '$data->abyssRank->ap',
					),
					array(
						'name'=>'gp',
						'type'=>'raw',
						'value' => '$data->abyssRank->gp',
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
					array(
						'name' => 'online',
						'type' => 'raw',
						'id' => 'online',
						'value' => 'Info::online($data->online)',
			        ),
			    ),
			));
		?>
		<div class="clear"></div>
	</div>
</div>