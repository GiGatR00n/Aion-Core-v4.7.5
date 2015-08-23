






<div class="note">
	<div class="note_title">
		<?php echo $model->title; ?>
		<?php if (!Yii::app()->user->isGuest AND Yii::app()->user->access_level >= Config::get('access_level_editor')): ?><a class="edit" title="Edit" href="<?php echo Yii::app()->homeUrl.'news/edit/'.$model->news_id; ?>"></a><?php endif; ?>
	</div>
	<div class="note_body h15 img">
		<?php if (!empty($model->full_story)) echo htmlspecialchars_decode($model->full_story);
		else echo htmlspecialchars_decode($model->short_story); ?>
	</div>
	<div class="note_info">
		<div class="right img_fix">
			<img src="<?php echo Yii::app()->theme->baseUrl; ?>/images/user.png" alt="" /> <?php echo $model->author->name; ?> |
			<img src="<?php echo Yii::app()->theme->baseUrl; ?>/images/calendar.png" alt="" /> <?php echo $model->date; ?> |
			<img src="<?php echo Yii::app()->theme->baseUrl; ?>/images/category.png" alt="" /> <a href="<?php echo Yii::app()->homeUrl.'category/'.$model->category['alt_name']; ?>"><?php echo $model->category['name']; ?></a>
		</div>
		<div class="clear"></div>
	</div>
</div>