<div class="note">
	<div class="note_body">
		<div class="note_title">Топ легионов</div>
		<table class="items">
			<tr>
				<th>Легион</td>
				<th width="95px">Раса</td>
				<th width="95px">Легат</td>
				<th width="95px">Участников</td>
				<th width="95px">Уровень</td>
				<th width="95px">Очки</td>
			</tr>
			<?php foreach ($model as $model): ?>
			<tr class="center">
				<td><a href="<?php echo Yii::app()->homeUrl.'legion/'.$model->name; ?>"><?php echo $model->name; ?></a></td>
				<td><?php echo Info::race($model->legat->players->race); ?></td>
				<td><a href="<?php echo Yii::app()->homeUrl.'player/'.$model->legat->players->name; ?>"><?php echo $model->legat->players->name; ?></a></td>
				<td><?php echo $model->membersCount; ?></td>
				<td><?php echo $model->level; ?></td>
				<td><?php echo $model->contribution_points; ?></td>
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