<ul class="menu_link">
<?php foreach($model as $data): ?>
	<li><a href="<?php echo Yii::app()->homeUrl.'category/'.$data->alt_name; ?>" title="<?php echo $data->title; ?>"><?php echo $data->name; ?></a>
<?php endforeach; ?>
</ul>