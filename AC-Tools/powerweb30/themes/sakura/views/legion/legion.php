<div class="note mb15">
	<div class="note_body">
		<div class="note_title">Информация о легионе <?php echo $model->name; ?></div>
		<table class="items">
			<tr>
				<td width="300">Название</td>
				<td><?php echo $model->name; ?></td>
			</tr>
			<tr>
				<td>Легат</td>
				<td><a href="<?php echo Yii::app()->homeUrl.'player/'.$model->legat->players->name; ?>"><?php echo $model->legat->players->name; ?></a></td>
			</tr>
			<tr>
				<td>Раса</td>
				<td><?php echo Info::race($model->legat->players->race); ?></td>
			</tr>
			<tr>
				<td>Уровень</td>
				<td><?php echo $model->level; ?></td>
			</tr>
			<tr>
				<td>Очки</td>
				<td><?php echo $model->contribution_points; ?></td>
			</tr>
			<tr>
				<td>Участников</td>
				<td><?php echo $model->membersCount; ?></td>
			</tr>
			<tr>
				<td>Дата создания</td>
				<td><?php echo Yii::app()->dateFormatter->format('dd.MM.y, HH:mm:ss', $model->legionHistory->date); ?></td>
			</tr>
		</table>
	</div>
</div>

<div class="note">
	<div class="note_body">
		<div class="note_title">Список персонажей легиона</div>
		<table class="items">
			<tr>
				<th>Имя</td>
				<th>Ранг</td>
				<th>Уровень</td>
				<th>Класс</td>
				<th>Статус</td>
			</tr>
			<?php foreach($players->legionMembers as $player): ?>
			<tr class="center">
				<td><a href="<?php echo Yii::app()->homeUrl.'player/'.$player['players']['name']; ?>"><?php echo $player['players']['name']; ?></a></td>
				<td><?php echo Info::legion_rank($player['rank']); ?></td>
				<td><?php echo Info::lvl($player->players['exp']); ?></td>
				<td><?php echo Info::player_class($player['players']['player_class']) ?></td>
				<td><?php echo Info::online($player['players']['online']); ?></td>
			</tr>
			<?php endforeach ?>
		</table>
	</div>
</div>