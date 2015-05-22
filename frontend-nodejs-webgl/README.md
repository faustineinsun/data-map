- Template from [https://github.com/faustineinsun/scalaJS-playground](https://github.com/faustineinsun/scalaJS-playground)

## On local [http://localhost:5000/](http://localhost:5000/)

```
$ npm install
$ foreman start web 
```

## On remote machine

* [AWS EC2](http://ec2-52-6-112-107.compute-1.amazonaws.com:5000/)

```
$ scripts/1-install-tools.sh
```

## Submit modifications

* Sync upstream
    * in `Overview` tab, message like `This fork is .. commits behind stonexjr/hadoophackathon. Sync now.` is been shown on the right 
    * click `Sync now`

* Fetch and merge code from your `master` branch to `frontend` branch

```
$ git checkout master 
$ git pull
$ git checkout -b frontend (if branch `frontend` exits, use $ `git checkout frontend` instead)
$ git merge master
```

* Modify codes in branch `frontend` -> folder `hadoophackathon/frontend-nodejs-webgl` 

```
$ vim views/index.html
$ vim public/js/webgl/webglmaptangram.js
$ vim public/css/webgl/webglmap.css
$ vim public/assets/scene.yaml
$ vim public/assets/yelpgeojson/analyzeddata-final.json
```

* Submit modifications
    * Make sure you are currently in branch `frontend` by $ `git branch`

```
$ git add --all
$ git commit -m "some notes" 
$ git push origin frontend
```

* [submit pull request](https://www.atlassian.com/git/tutorials/making-a-pull-request/how-it-works)
    * from `your-repo-frontend-branch` to `stonexjr/hadoophackathon-master-branch`

* in `Branches` tab, merge `your-repo-frontend-branch` to `your-repo-master-branch` 

