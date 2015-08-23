<div class="note">
	<div class="note_body">
		<div class="note_title">Игроки онлайн</div>
		<table class="items">
			<tr>
				<th>Имя</td>
				<th width="95px">Уровень</td>
				<th width="95px">AP</td>
				<th width="95px">Раса</td>
				<th width="95px">Класс</td>
				<th width="95px">Локация</td>
			</tr>
			<?php foreach ($model as $model): ?>
			<tr class="center">
				<td><a href="<?php echo Yii::app()->homeUrl.'player/'.$model->name; ?>"><?php echo $model->name; ?></a></td>
				<td><?php echo Info::lvl($model->exp); ?></td>
				<td><?php echo number_format($model->abyssRank['ap'],0,' ',' '); ?></td>
				<td><?php echo Info::race($model->race) ?></td>
				<td><?php echo Info::player_class($model->player_class) ?></td>
				<td><?php if ($model->show_location == 1): echo Info::world($model->world_id); ?><?php else: ?>Скрыто<?php endif; ?></td>
			</tr>
			<?php endforeach ?>
		</table>
	</div>
</div>


<div class="pages">
	<?php $this->widget('CLinkPager',array(
		'pages'=>$pages,
		'maxButtonCount' => 7,
		'nextPageLabel' => '&rarr;',
		'prevPageLabel' => '&larr;',
		'firstPageLabel' => '<<',
		'lastPageLabel' => '>>',
		'header' => '',
	)); ?>
</div>