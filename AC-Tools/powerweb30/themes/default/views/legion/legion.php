






<div class="note mb15">
	<div class="note_title">
		Legion <?php echo $model->name; ?> information
	</div>
	<div class="note_body">
		<table class="items">
			<tr>
				<td width="300">Title</td>
				<td><?php echo $model->name; ?></td>
			</tr>
			<tr>
				<td>Legate</td>
				<td><a href="<?php echo Yii::app()->homeUrl.'player/'.$model->legat->players->name; ?>"><?php echo $model->legat->players->name; ?></a></td>
			</tr>
			<tr>
				<td>Race</td>
				<td><?php echo Info::race($model->legat->players->race); ?></td>
			</tr>
			<tr>
				<td>Level</td>
				<td><?php echo $model->level; ?></td>
			</tr>
			<tr>
				<td>Score</td>
				<td><?php echo $model->contribution_points; ?></td>
			</tr>
			<tr>
				<td>Members</td>
				<td><?php echo $model->membersCount; ?></td>
			</tr>
			<tr>
				<td>Date created</td>
				<td><?php echo Yii::app()->dateFormatter->format('dd.MM.y, HH:mm:ss', $model->legionHistory->date); ?></td>
			</tr>
		</table>
	</div>
</div>


<div class="note">
	<div class="note_title">
		Legion member list
	</div>
	<div class="note_body">
		<table class="items">
			<tr>
				<th>Name</td>
				<th>Rank</td>
				<th>Level</td>
				<th>Class</td>
				<th>Status</td>
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