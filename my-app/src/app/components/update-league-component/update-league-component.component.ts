import { Component, OnInit } from '@angular/core';
import { LeagueDto } from "../../common/league-dto";
import { LeagueService } from "../../services/league.service";
import { ActivatedRoute, Router } from "@angular/router";
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ImageFileValidator } from "../../validators/image-file-validator";

@Component({
  selector: 'app-update-league-component',
  templateUrl: './update-league-component.component.html',
  styleUrls: ['./update-league-component.component.css']
})
export class UpdateLeagueComponent implements OnInit {

  league: LeagueDto = new LeagueDto(0, '', 0, '', ''); // Initialize league with empty values
  editForm: FormGroup;
  selectedFile: File | null = null;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private leagueService: LeagueService,
    private formBuilder: FormBuilder
  ) {
    this.editForm = this.formBuilder.group({
      name: ['', [Validators.required]],
      founded: ['', [Validators.required]],
      description: ['', [Validators.required]],
      file: [null, [ImageFileValidator.invalidImageType]]
    });
  }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      const leagueId = params['id'];
      const leagueData = history.state.league;

      if (leagueData) {
        this.league = leagueData;
        this.getLeagueImageUrl(this.league.picture);
        this.patchForm(this.league);
      } else {
        this.leagueService.returnLeagueById(leagueId).subscribe((data: LeagueDto) => {
          this.league = data;
          this.getLeagueImageUrl(data.picture);
          this.patchForm(data);
        }, error => {
          console.error('Failed to fetch league data', error);
        });
      }
    });
  }

  updateLeague(): void {
    if (this.editForm.valid) {
      const formValue = this.editForm.value;
      const updatedLeague = new LeagueDto(
        this.league.id,
        formValue.name,
        formValue.founded,
        formValue.description,
        this.league.picture
      );

      this.leagueService.editLeague(updatedLeague, this.selectedFile).subscribe(() => {
        this.router.navigate(['/league']);
      }, error => {
        alert('Failed to update league. Please try again.');
      });
    } else {
      this.editForm.markAllAsTouched();
    }
  }

  onFileChange(event: any) {
    const file = event.target.files[0];
    this.selectedFile = file;

    // Preview the selected image
    const reader = new FileReader();
    reader.onload = (e: any) => {
      this.league.picture = e.target.result; // This is for previewing the image
    };
    reader.readAsDataURL(file);
  }

  getLeagueImageUrl(imagePath: string): void {
    if (imagePath) {
      this.leagueService.getImageUrl(imagePath).subscribe((blob: Blob) => {
        const imageUrl = URL.createObjectURL(blob);
        this.league.picture = imageUrl;
      }, error => {
        console.error(`Error fetching image for league: ${imagePath}`, error);
        this.league.picture = ''; // Set default image path
      });
    }
  }

  patchForm(league: LeagueDto): void {
    this.editForm.patchValue({
      name: league.name,
      founded: league.founded,
      description: league.description,
      file: null // File input should be empty
    });
  }
}
